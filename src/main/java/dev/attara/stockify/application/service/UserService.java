package dev.attara.stockify.application.service;

import dev.attara.stockify.application.dto.input.CreateUserDTO;
import dev.attara.stockify.application.dto.input.UpdateUserDTO;
import dev.attara.stockify.application.dto.output.UserRecord;

import java.util.List;

public interface UserService {

    UserRecord createUser(CreateUserDTO createUserDTO);

    UserRecord updateUser(long userId, UpdateUserDTO updateUserDTO);

    List<UserRecord> allUsers();

    UserRecord getUserById(long id);

    Boolean deleteUser(long id);

}
