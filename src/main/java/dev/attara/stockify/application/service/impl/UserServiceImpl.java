package dev.attara.stockify.application.service.impl;

import dev.attara.stockify.application.dto.input.CreateUserDTO;
import dev.attara.stockify.application.dto.input.UpdateUserDTO;
import dev.attara.stockify.application.dto.output.UserRecord;
import dev.attara.stockify.application.service.UserService;
import dev.attara.stockify.domain.exception.UserExistsException;
import dev.attara.stockify.domain.model.Role;
import dev.attara.stockify.domain.model.User;
import dev.attara.stockify.domain.repository.UserRepository;
import dev.attara.stockify.infrastructure.persistence.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserRecord createUser(CreateUserDTO createUserDTO) {
        if (userRepository.userEmailExists(createUserDTO.email())) throw new UserExistsException(createUserDTO.email());
        Long id = userRepository.nextId();
        String name = createUserDTO.name();
        String email = createUserDTO.email();
        String password = passwordEncoder.encode(createUserDTO.password());
        Role role = createUserDTO.role();
        if (role == null) {
            role = Role.USER;
        }
        User user = new User(id, name, email, password, role);
        userRepository.save(user);
        return new UserRecord(id, name, email, role);
    }

    @Override
    public UserRecord updateUser(Long userId, UpdateUserDTO updateUserDTO) {
        User user = userRepository.findById(userId);
        if (updateUserDTO.email() != null) {
            user.setEmail(updateUserDTO.email());
        }
        if (updateUserDTO.name() != null) {
            user.setName(updateUserDTO.name());
        }
        if (updateUserDTO.password() != null) {
            user.setPassword(updateUserDTO.password());
        }
        if (updateUserDTO.role() != null) {
            user.setRole(updateUserDTO.role());
        }
        userRepository.save(user);
        return userMapper.toRecord(user);
    }

    @Override
    public List<UserRecord> allUsers() {
        return userRepository.findAll().stream().map(userMapper::toRecord).toList();
    }
    @Override
    public UserRecord getUserById(Long id) {
        User user = userRepository.findById(id);
        return userMapper.toRecord(user);
    }

    @Override
    public Boolean deleteUser(Long id) {
        User user = userRepository.findById(id);
        userRepository.delete(user);
        return true;
    }
}
