package dev.attara.stockify.interfaces.graphql;

import dev.attara.stockify.application.dto.input.CreateUserDTO;
import dev.attara.stockify.application.dto.input.UpdateUserDTO;
import dev.attara.stockify.application.dto.output.UserRecord;
import dev.attara.stockify.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @MutationMapping
    public UserRecord createUser(@Argument CreateUserDTO user) {
        return userService.createUser(user);
    }

    @MutationMapping
    public boolean deleteUser(@Argument Long id) {
        return userService.deleteUser(id);
    }

    @MutationMapping
    public UserRecord updateUser(@Argument Long id, @Argument UpdateUserDTO data) {
        return userService.updateUser(id, data);
    }

    @QueryMapping
    public List<UserRecord> users() {
        return userService.allUsers();
    }

    @QueryMapping
    public UserRecord user(@Argument Long id) {
        return userService.getUserById(id);
    }
}
