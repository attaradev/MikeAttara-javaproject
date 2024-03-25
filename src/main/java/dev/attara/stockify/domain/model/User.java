package dev.attara.stockify.domain.model;

import lombok.*;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class User {
    private final Long id;
    @NotNull private String name;
    @NotNull private String email;
    @NotNull private String password;
    private Role role;
}
