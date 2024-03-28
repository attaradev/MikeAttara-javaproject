package dev.attara.stockify.interfaces.graphql;

import dev.attara.stockify.application.dto.UserRecord;
import dev.attara.stockify.application.security.AuthenticatedUserProvider;
import dev.attara.stockify.application.service.usermanagement.adduser.AddUser;
import dev.attara.stockify.application.service.usermanagement.adduser.AddUserHandler;
import dev.attara.stockify.application.service.usermanagement.deleteuser.DeleteUser;
import dev.attara.stockify.application.service.usermanagement.deleteuser.DeleteUserHandler;
import dev.attara.stockify.application.service.usermanagement.getallusers.GetAllUsers;
import dev.attara.stockify.application.service.usermanagement.getallusers.GetAllUsersHandler;
import dev.attara.stockify.application.service.usermanagement.getuser.GetUser;
import dev.attara.stockify.application.service.usermanagement.getuser.GetUserHandler;
import dev.attara.stockify.application.service.usermanagement.updateemail.UpdateEmail;
import dev.attara.stockify.application.service.usermanagement.updateemail.UpdateEmailHandler;
import dev.attara.stockify.application.service.usermanagement.updatename.UpdateName;
import dev.attara.stockify.application.service.usermanagement.updatename.UpdateNameHandler;
import dev.attara.stockify.application.service.usermanagement.updatepassword.UpdatePassword;
import dev.attara.stockify.application.service.usermanagement.updatepassword.UpdatePasswordHandler;
import dev.attara.stockify.application.service.usermanagement.updaterole.UpdateRole;
import dev.attara.stockify.application.service.usermanagement.updaterole.UpdateRoleHandler;
import dev.attara.stockify.domain.exception.NotAuthorizedException;
import dev.attara.stockify.domain.model.Role;
import dev.attara.stockify.domain.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
public class UserController {

    private final AuthenticatedUserProvider userProvider;

    private final AddUserHandler addUserHandler;

    private final DeleteUserHandler deleteUserHandler;

    private final UpdatePasswordHandler updatePasswordHandler;

    private final UpdateRoleHandler updateRoleHandler;

    private final UpdateEmailHandler updateEmailHandler;

    private final UpdateNameHandler updateNameHandler;

    private final GetUserHandler getUserHandler;

    private final GetAllUsersHandler getAllUsersHandler;

    @MutationMapping
    @Secured("ROLE_ADMIN")
    public UserRecord createUser(@Argument AddUser userData) {
        log.info("Creating user with data: {}", userData);
        UserRecord userRecord = addUserHandler.handle(userData);
        log.info("User created successfully: {}", userRecord);
        return userRecord;
    }

    @MutationMapping
    @Secured("ROLE_ADMIN")
    public boolean deleteUser(@Argument long userId) {
        log.info("Deleting user with ID: {}", userId);
        boolean result = deleteUserHandler.handle(new DeleteUser(userId));
        log.info("User deleted successfully with ID: {}", userId);
        return result;
    }

    @MutationMapping
    public UserRecord updateEmail(@Argument long userId, @Argument String newEmail, Principal principal) {
        User user = userProvider.get(principal);
        selfOrAdminOnly(userId, user);
        log.info("Updating email for user ID: {} with new email: {}", userId, newEmail);
        UserRecord userRecord = updateEmailHandler.handle(new UpdateEmail(userId, newEmail));
        log.info("Email updated successfully for user ID: {}", userId);
        return userRecord;
    }

    @MutationMapping
    public UserRecord updateName(@Argument long userId, @Argument String newName, Principal principal) {
        User user = userProvider.get(principal);
        selfOrAdminOnly(userId, user);
        log.info("Updating name for user ID: {} with new name: {}", userId, newName);
        UserRecord userRecord = updateNameHandler.handle(new UpdateName(userId, newName));
        log.info("Name updated successfully for user ID: {}", userId);
        return userRecord;
    }

    @MutationMapping
    @Secured("ROLE_ADMIN")
    public UserRecord updateRole(@Argument long userId, @Argument Role newRole) throws Exception {
        log.info("Updating role for user ID: {} with new role: {}", userId, newRole);
        UserRecord userRecord = updateRoleHandler.handle(new UpdateRole(userId, newRole));
        log.info("Role updated successfully for user ID: {}", userId);
        return userRecord;
    }

    @MutationMapping
    public UserRecord updatePassword(@Argument long userId, @Argument String newPassword, Principal principal) {
        User user = userProvider.get(principal);
        if (user.getId() != userId) {
            log.error("User with ID: {} is not authorized to change password for user with ID: {}", user.getId(), userId);
            throw new NotAuthorizedException();
        }
        log.info("Updating password for user ID: {}", userId);
        UserRecord userRecord = updatePasswordHandler.handle(new UpdatePassword(userId, newPassword));
        log.info("Password updated successfully for user ID: {}", userId);
        return userRecord;
    }

    @QueryMapping
    @Secured("ROLE_ADMIN")
    public List<UserRecord> users() {
        log.info("Retrieving all users");
        List<UserRecord> users = getAllUsersHandler.handle(new GetAllUsers());
        log.info("Retrieved all users: {}", users);
        return users;
    }

    @QueryMapping
    public UserRecord user(@Argument long userId, Principal principal) {
        User user = userProvider.get(principal);
        selfOrAdminOnly(userId, user);
        log.info("Retrieving user with ID: {}", userId);
        UserRecord userRecord = getUserHandler.handle(new GetUser(userId));
        log.info("Retrieved user with ID {}: {}", userId, userRecord);
        return userRecord;
    }

    private void selfOrAdminOnly(long userId, User user) {
        if (user.isNotAdmin() && userId != user.getId()) {
            log.error("User with ID: {} is not authorized to access user with ID: {}", user.getId(), userId);
            throw new NotAuthorizedException();
        }
    }
}
