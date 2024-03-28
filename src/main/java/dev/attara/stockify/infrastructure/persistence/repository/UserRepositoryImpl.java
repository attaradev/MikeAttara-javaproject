package dev.attara.stockify.infrastructure.persistence.repository;

import dev.attara.stockify.domain.exception.UserNotFoundException;
import dev.attara.stockify.domain.model.User;
import dev.attara.stockify.domain.repository.UserRepository;
import dev.attara.stockify.infrastructure.persistence.entity.UserEntity;
import dev.attara.stockify.infrastructure.persistence.mapper.UserMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of the UserRepository interface for accessing and managing UserEntity objects in the database.
 */
@Repository
@Transactional
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserMapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id The unique identifier of the user.
     * @return The user with the specified ID.
     * @throws UserNotFoundException If no user exists with the given ID.
     */
    @Override
    public User findById(long id) throws UserNotFoundException {
        UserEntity user = entityManager.find(UserEntity.class, id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        return mapper.mapToDomain(user);
    }

    /**
     * Retrieves a user by their email address.
     *
     * @param email The email address of the user.
     * @return The user with the specified email address.
     * @throws UserNotFoundException If no user exists with the given email address.
     */
    @Override
    public User findByEmail(String email) throws UserNotFoundException {
        try {
            UserEntity userEntity = entityManager.createQuery(
                            "SELECT u FROM UserEntity u WHERE u.email = :email", UserEntity.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return mapper.mapToDomain(userEntity);
        } catch (NoResultException e) {
            throw new UserNotFoundException("User not found");
        }
    }

    /**
     * Checks if a user with the given email address exists in the database.
     *
     * @param email The email address to check.
     * @return true if the user exists, false otherwise.
     */
    @Override
    public boolean userEmailExists(String email) {
        try {
            entityManager.createQuery(
                            "SELECT u FROM UserEntity u WHERE u.email = :email", UserEntity.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    /**
     * Retrieves the total number of users in the database.
     *
     * @return The total number of users.
     */
    @Override
    public long count() {
        return findAll().size();
    }

    /**
     * Retrieves all users from the database.
     *
     * @return A list of all users.
     */
    @Override
    public List<User> findAll() {
        List<UserEntity> userEntities = entityManager.createQuery("SELECT u FROM UserEntity u", UserEntity.class).getResultList();
        return userEntities.stream().map(mapper::mapToDomain).toList();
    }

    /**
     * Saves a user in the database.
     *
     * @param user The user to save.
     */
    @Override
    public void save(User user) {
        entityManager.merge(mapper.mapToEntity(user));
        entityManager.flush();
    }

    /**
     * Deletes a user from the database.
     *
     * @param user The user to delete.
     */
    @Override
    public void delete(User user) {
        UserEntity managedUser = entityManager.find(UserEntity.class, user.getId());
        entityManager.remove(managedUser);
    }
}
