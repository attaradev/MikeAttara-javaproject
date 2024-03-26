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
    public UserRecord createUser(CreateUserDTO dto) {
        if (userRepository.userEmailExists(dto.email())) throw new UserExistsException(dto.email());
        long id = userRepository.nextId();
        String name = dto.name();
        String email = dto.email();
        String password = passwordEncoder.encode(dto.password());
        Role role = dto.role();
        User user = User.create(id, email, password, name, role);
        userRepository.save(user);
        return new UserRecord(id, name, email, role);
    }

    @Override
    public UserRecord updateUser(long userId, UpdateUserDTO dto) {
        User user = userRepository.findById(userId);
        if (dto.email() != null) {
            user.setEmail(dto.email());
        }
        if (dto.name() != null) {
            user.setName(dto.name());
        }
        if (dto.password() != null) {
            user.setPassword(dto.password());
        }
        if (dto.role() != null) {
            user.setRole(dto.role());
        }
        userRepository.save(user);
        return userMapper.mapToRecord(user);
    }

    @Override
    public List<UserRecord> allUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::mapToRecord)
                .toList();
    }
    @Override
    public UserRecord getUserById(long userId) {
        User user = userRepository.findById(userId);
        return userMapper.mapToRecord(user);
    }

    @Override
    public Boolean deleteUser(long userId) {
        User user = userRepository.findById(userId);
        userRepository.delete(user);
        return true;
    }

}
