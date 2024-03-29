package dev.attara.stockify.domain.repository;

import dev.attara.stockify.domain.exception.UserNotFoundException;
import dev.attara.stockify.domain.model.User;

/**
 * Repository interface for managing users.
 */
public interface UserRepository extends BaseRepository<User> {

    /**
     * Finds and returns a user by their ID.
     *
     * @param id the ID of the user to find
     * @return the found user
     * @throws UserNotFoundException if the user with the given ID is not found
     */
    User findById(String id) throws UserNotFoundException;

    /**
     * Finds and returns a user by their email address.
     *
     * @param email the email address of the user to find
     * @return the found user
     * @throws UserNotFoundException if the user with the given email address is not found
     */
    User findByEmail(String email) throws UserNotFoundException;

    /**
     * Checks if a user with the given email address exists.
     *
     * @param email the email address to check
     * @return true if a user with the given email exists, false otherwise
     */
    boolean userEmailExists(String email);

    /**
     * Counts the total number of users in the repository.
     *
     * @return the total number of users
     */
    long count();
}
