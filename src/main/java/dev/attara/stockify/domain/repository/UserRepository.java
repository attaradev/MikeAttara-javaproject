package dev.attara.stockify.domain.repository;

import dev.attara.stockify.domain.exception.UserNotFoundException;
import dev.attara.stockify.domain.model.User;

public interface UserRepository extends BaseRepository<User> {
    User findById(Long id) throws UserNotFoundException;
    User findByEmail(String email) throws UserNotFoundException;

    boolean userEmailExists(String email);

    Long count();
}
