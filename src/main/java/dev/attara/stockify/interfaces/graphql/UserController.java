package dev.attara.stockify.interfaces.graphql;

import dev.attara.stockify.application.dto.input.CreateUserDTO;
import dev.attara.stockify.application.dto.input.UpdateUserDTO;
import dev.attara.stockify.application.dto.output.UserRecord;
import dev.attara.stockify.application.security.AuthenticatedUserProvider;
import dev.attara.stockify.application.service.UserService;
import dev.attara.stockify.domain.exception.NotAuthorizedException;
import dev.attara.stockify.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
public class UserController {

    private final UserService userService;

    private final AuthenticatedUserProvider authenticatedUserProvider;

    @MutationMapping
    @Secured("ROLE_ADMIN")
    public UserRecord createUser(@Argument CreateUserDTO user) {
        return userService.createUser(user);
    }

    @MutationMapping
    @Secured("ROLE_ADMIN")
    public boolean deleteUser(@Argument long userId) {
        return userService.deleteUser(userId);
    }

    @MutationMapping
    public UserRecord updateUser(@Argument long userId, @Argument UpdateUserDTO userData, Principal principal) {
        User user = authenticatedUserProvider.user(principal);
        selfOrAdminOnly(userId, user);
        return userService.updateUser(userId, userData);
    }

    @QueryMapping
    @Secured("ROLE_ADMIN")
    public List<UserRecord> users() {
        return userService.allUsers();
    }

    @QueryMapping
    public UserRecord user(@Argument long userId, Principal principal) {
        User user = authenticatedUserProvider.user(principal);
        selfOrAdminOnly(userId, user);
        return userService.getUserById(userId);
    }

    private void selfOrAdminOnly(long userId, User user) {
        if (user.isNotAdmin() && userId != user.getId()) throw new NotAuthorizedException();
    }
}
