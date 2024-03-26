package dev.attara.stockify.infrastructure.persistence.mapper;

import dev.attara.stockify.application.dto.output.UserRecord;
import dev.attara.stockify.domain.model.User;
import dev.attara.stockify.infrastructure.persistence.entity.UserEntity;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User mapToDomain(@NonNull UserEntity entity) {
        return User.create(
                entity.getId(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getName(),
                entity.getRole()
        );
    }

    public UserRecord mapToRecord(@NonNull User model) {
        return new UserRecord(
                model.getId(),
                model.getName(),
                model.getEmail(),
                model.getRole()
        );
    }

    public UserEntity mapToEntity(@NonNull User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        entity.setRole(user.getRole());
        return entity;
    }

}
