package dev.attara.stockify.application.service.impl;

import dev.attara.stockify.application.dto.input.CreateUserDTO;
import dev.attara.stockify.application.dto.input.UpdateUserDTO;
import dev.attara.stockify.application.dto.output.UserRecord;
import dev.attara.stockify.domain.exception.UserExistsException;
import dev.attara.stockify.domain.model.Role;
import dev.attara.stockify.domain.model.User;
import dev.attara.stockify.domain.repository.UserRepository;
import dev.attara.stockify.infrastructure.persistence.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser_Success() {
        // Arrange
        CreateUserDTO createUserDTO = new CreateUserDTO("john@example.com", "password","John Doe",  Role.USER);
        UserRecord expectedUserRecord = new UserRecord(1L, "John Doe", "john@example.com", Role.USER);
        when(userRepository.userEmailExists(createUserDTO.email())).thenReturn(false);
        when(userRepository.nextId()).thenReturn(1L);
        when(passwordEncoder.encode(createUserDTO.password())).thenReturn("encodedPassword");
        User user = User.create(1L, createUserDTO.email(), "encodedPassword", createUserDTO.name(), createUserDTO.role());
        when(userMapper.mapToRecord(user)).thenReturn(expectedUserRecord);

        // Act
        UserRecord actualUserRecord = userService.createUser(createUserDTO);

        // Assert
        assertEquals(expectedUserRecord, actualUserRecord);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testCreateUser_UserExistsException() {
        // Arrange
        CreateUserDTO createUserDTO = new CreateUserDTO("John Doe", "john@example.com", "password", Role.USER);
        when(userRepository.userEmailExists(createUserDTO.email())).thenReturn(true);

        // Act and Assert
        assertThrows(UserExistsException.class, () -> userService.createUser(createUserDTO));
        verify(userRepository, never()).save(any());
    }

    @Test
    void testUpdateUser() {
        // Arrange
        long userId = 1L;
        UpdateUserDTO updateUserDTO = new UpdateUserDTO("Updated Name", "updated@example.com", "updatedPassword", Role.ADMIN);
        User user = User.create(userId, "original@example.com", "originalPassword","Original Name",  Role.USER);
        when(userRepository.findById(userId)).thenReturn(user);
        when(userMapper.mapToRecord(user)).thenReturn(new UserRecord(userId, "Updated Name", "updated@example.com", Role.ADMIN));
        // Act
        UserRecord updatedUserRecord = userService.updateUser(userId, updateUserDTO);

        // Assert
        assertEquals(updateUserDTO.name(), updatedUserRecord.name());
        assertEquals(updateUserDTO.email(), updatedUserRecord.email());
        assertEquals(updateUserDTO.role(), updatedUserRecord.role());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testAllUsers() {
        // Arrange
        List<User> userList = new ArrayList<>();
        User admin = User.create(1L, "John Doe", "john@example.com", "password", Role.ADMIN);
        User user = User.create(2L, "John Doe", "john@example.com", "password", Role.USER);
        userList.add(admin);
        userList.add(user);
        when(userRepository.findAll()).thenReturn(userList);
        when(userMapper.mapToRecord(user)).thenReturn(new UserRecord(user.getId(), user.getName(), user.getEmail(), user.getRole()));
        when(userMapper.mapToRecord(admin)).thenReturn(new UserRecord(admin.getId(), admin.getName(), admin.getEmail(), admin.getRole()));

        // Act
        List<UserRecord> actualUserRecords = userService.allUsers();

        // Assert
        List<UserRecord> expectedUserRecords = new ArrayList<>();
        for (User user1 : userList) {
            expectedUserRecords.add(new UserRecord(user1.getId(), user1.getName(), user1.getEmail(), user1.getRole()));
        }
        assertEquals(expectedUserRecords, actualUserRecords);
    }

    @Test
    void testGetUserById() {
        // Arrange
        long userId = 1L;
        User user = User.create(userId, "John Doe", "john@example.com", "password", Role.USER);
        when(userRepository.findById(userId)).thenReturn(user);
        UserRecord expectedUserRecord = new UserRecord(userId, user.getName(), user.getEmail(), user.getRole());
        when(userMapper.mapToRecord(user)).thenReturn(expectedUserRecord);

        // Act
        UserRecord actualUserRecord = userService.getUserById(userId);

        // Assert
        assertEquals(expectedUserRecord, actualUserRecord);
    }

    @Test
    void testDeleteUser() {
        // Arrange
        long userId = 1L;
        User user = User.create(userId, "John Doe", "john@example.com", "password", Role.USER);
        when(userRepository.findById(userId)).thenReturn(user);

        // Act
        boolean result = userService.deleteUser(userId);

        // Assert
        assertTrue(result);
        verify(userRepository, times(1)).delete(user);
    }
}
