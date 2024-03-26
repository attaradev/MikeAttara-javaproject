package dev.attara.stockify.infrastructure.persistence.mapper;

import dev.attara.stockify.application.dto.output.UserRecord;
import dev.attara.stockify.domain.model.User;
import dev.attara.stockify.infrastructure.persistence.entity.UserEntity;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toModel(@NonNull UserEntity entity) {
        return User.create(
                entity.getId(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getName(),
                entity.getRole()
        );
    }

    public UserRecord toRecord(@NonNull User model) {
        return new UserRecord(
                model.getId(),
                model.getName(),
                model.getEmail(),
                model.getRole()
        );
    }

    public UserEntity toEntity(@NonNull User model) {
        UserEntity entity = new UserEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setEmail(model.getEmail());
        entity.setPassword(model.getPassword());
        entity.setRole(model.getRole());
        return entity;
    }

}
