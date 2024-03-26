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

@Repository
@Transactional
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserMapper mapper;


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findById(long id) throws UserNotFoundException {
        UserEntity user = entityManager.find(UserEntity.class, id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        return mapper.mapToDomain(user);
    }

    @Override
    public User findByEmail(String email) throws UserNotFoundException {
        try {
            UserEntity userEntity = entityManager.createQuery(
                            "SELECT u FROM UserEntity u WHERE u.email = :email", UserEntity.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return mapper.mapToDomain(userEntity);
        } catch (NoResultException e) {
            throw new UserNotFoundException();
        }
    }

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

    @Override
    public long count() {
        return findAll().size();
    }


    @Override
    public List<User> findAll() {
        List<UserEntity> userEntities = entityManager.createQuery("SELECT u FROM UserEntity u", UserEntity.class).getResultList();
        return userEntities.stream().map(mapper::mapToDomain).toList();
    }

    @Override
    public void save(User user) {
        entityManager.merge(mapper.mapToEntity(user));
    }

    @Override
    public void delete(User user) {
        entityManager.remove(mapper.mapToEntity(user));
    }

}
