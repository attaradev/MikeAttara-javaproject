package dev.attara.stockify.domain.repository;

import dev.attara.stockify.domain.exception.UserNotFoundException;
import dev.attara.stockify.domain.model.User;

public interface UserRepository extends BaseRepository<User> {
    User findById(long id) throws UserNotFoundException;
    User findByEmail(String email) throws UserNotFoundException;

    boolean userEmailExists(String email);

    long count();
}
