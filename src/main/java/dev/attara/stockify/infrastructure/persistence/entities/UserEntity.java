package dev.attara.stockify.infrastructure.persistence.entities;

import dev.attara.stockify.domain.models.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Represents a user entity in the database.
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
public class UserEntity {

    /** The unique identifier of the user. */
    @Id
    private String id;

    /** The name of the user. */
    @Column
    private String name;

    /** The email of the user. */
    @Column(nullable = false, unique = true)
    private String email;

    /** The password of the user. */
    @Column(nullable = false)
    private String password;

    /** The role of the user. */
    @Enumerated(EnumType.STRING)
    private Role role;

    /** The list of orders associated with this user. */
    @OneToMany(mappedBy = "user")
    private List<OrderEntity> orders;

}
