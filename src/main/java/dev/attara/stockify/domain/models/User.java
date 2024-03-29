package dev.attara.stockify.domain.models;

import lombok.*;

@Getter
@Setter
public class User {

    private final String id;

    @NonNull
    private String email;

    @NonNull
    private String password;

    private String name;

    private Role role;

    private User(String id, @NonNull String email, @NonNull String password, String name, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role != null ? role : Role.USER; // Set default role if null
    }

    /**
     * Creates a new user.
     *
     * @param id       the ID of the user
     * @param email    the email of the user
     * @param password the password of the user
     * @param name     the name of the user
     * @param role     the role of the user
     * @return the created user
     * @throws IllegalArgumentException if the ID is invalid, email or password is null
     */
    public static User create(String id, @NonNull String email, @NonNull String password, String name, Role role) {
        if (id == null) throw new IllegalArgumentException("ID must be greater than 0");
        if (email.isBlank()) throw new IllegalArgumentException("Email cannot be blank");
        if (password.isBlank()) throw new IllegalArgumentException("Password cannot be blank");
        return new User(id, email, password, name, role);
    }

    /**
     * Checks if the user is not an admin.
     *
     * @return true if the user is not an admin, false otherwise
     */
    public boolean isNotAdmin() {
        return role != Role.ADMIN;
    }

    /**
     * Compares this user to the specified object. The result is true if and only if the argument
     * is not null and is a User object with the same ID.
     *
     * @param o the object to compare this user against
     * @return true if the given object represents a User with the same ID, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return id.equals(user.id);
    }

}
