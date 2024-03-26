package dev.attara.stockify.domain.model;

import lombok.*;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class User {

    private final long id;

    @NotNull private String email;

    @NotNull private String password;

    private String name;

    private Role role;

    private User(long id, @NonNull String email, @NonNull String password, String name, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public static User create(long id, String email, String password, String name, Role role) throws IllegalArgumentException {
        if (id < 1) throw new IllegalArgumentException("id required");
        if (email == null) throw new IllegalArgumentException("email required");
        if (password == null) throw new IllegalArgumentException("password required");
        if (role == null) {
            role = Role.USER;
        }
        return new User(id, email, password, name, role);
    }

    public boolean isNotAdmin() {
        return role != Role.ADMIN;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        return (this.getClass() == o.getClass() && id == ((User) o).id);
    }

}
