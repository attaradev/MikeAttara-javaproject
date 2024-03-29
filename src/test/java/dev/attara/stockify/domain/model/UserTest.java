package dev.attara.stockify.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void testCreateUser() {
        User user = User.create("1", "test@example.com", "password", "Test User", Role.USER);
        assertNotNull(user);
        assertEquals("1", user.getId());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals("Test User", user.getName());
        assertEquals(Role.USER, user.getRole());
    }

    @Test
    void testCreateUserWithDefaultRole() {
        User user = User.create("1", "test@example.com", "password", "Test User", null);
        assertNotNull(user);
        assertEquals("1", user.getId());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals("Test User", user.getName());
        assertEquals(Role.USER, user.getRole());
    }

    @Test
    void testCreateUserWithInvalidId() {
        assertThrows(IllegalArgumentException.class, () -> User.create(null, "test@example.com", "password", "Test User", Role.USER));
    }

    @Test
    void testIsNotAdmin() {
        User user = User.create("1", "test@example.com", "password", "Test User", Role.USER);
        assertTrue(user.isNotAdmin());
    }

    @Test
    void testEqualsSameObject() {
        User user = User.create("1", "test@example.com", "password", "Test User", Role.USER);
        assertEquals(user, user);
    }

    @Test
    void testEqualsDifferentObjects() {
        User user1 = User.create("1", "test@example.com", "password", "Test User", Role.USER);
        User user2 = User.create("1", "test@example.com", "password", "Test User", Role.USER);
        assertEquals(user1, user2);
    }

    @Test
    void testEqualsDifferentClasses() {
        User user = User.create("1", "test@example.com", "password", "Test User", Role.USER);
        assertNotEquals(user, new Object());
    }
}
