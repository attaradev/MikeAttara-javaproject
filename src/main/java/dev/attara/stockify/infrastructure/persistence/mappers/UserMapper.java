package dev.attara.stockify.infrastructure.persistence.mappers;

import dev.attara.stockify.application.dtos.UserRecord;
import dev.attara.stockify.application.mappers.Mapper;
import dev.attara.stockify.domain.models.User;
import dev.attara.stockify.infrastructure.persistence.entities.UserEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper class responsible for mapping User objects between their domain representation, database entity, and DTO representation.
 */
@Component
public class UserMapper implements Mapper<User, UserRecord, UserEntity> {

    /**
     * Maps a UserEntity object from the database to its corresponding domain model representation.
     *
     * @param entity The UserEntity object to map.
     * @return The mapped User domain model object.
     */
    public User mapToDomain(UserEntity entity) {
        return User.create(
                entity.getId(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getName(),
                entity.getRole()
        );
    }

    /**
     * Maps a User domain model object to its corresponding DTO representation.
     *
     * @param model The User domain model object to map.
     * @return The mapped UserRecord DTO object.
     */
    public UserRecord mapToRecord(User model) {
        return new UserRecord(
                model.getId(),
                model.getName(),
                model.getEmail(),
                model.getRole()
        );
    }

    /**
     * Maps a User domain model object to its corresponding database entity representation.
     *
     * @param user The User domain model object to map.
     * @return The mapped UserEntity database entity object.
     */
    public UserEntity mapToEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        entity.setRole(user.getRole());
        return entity;
    }

}
