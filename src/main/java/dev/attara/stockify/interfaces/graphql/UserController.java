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
    public boolean deleteUser(@Argument long id) {
        return userService.deleteUser(id);
    }

    @MutationMapping
    public UserRecord updateUser(@Argument long id, @Argument UpdateUserDTO data, Principal principal) {
        User user = authenticatedUserProvider.user(principal);
        selfOrAdminOnly(id, user);
        return userService.updateUser(id, data);
    }

    @QueryMapping
    @Secured("ROLE_ADMIN")
    public List<UserRecord> users() {
        return userService.allUsers();
    }

    @QueryMapping
    public UserRecord user(@Argument long id, Principal principal) {
        User user = authenticatedUserProvider.user(principal);
        selfOrAdminOnly(id, user);
        return userService.getUserById(id);
    }

    private void selfOrAdminOnly(long id, User user) {
        if (user.isNotAdmin() && id != user.getId()) throw new NotAuthorizedException();
    }
}
