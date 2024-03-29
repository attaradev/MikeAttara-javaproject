package dev.attara.stockify.domain.repositories;

import java.util.List;

/**
 * Base repository interface defining common operations for repositories.
 *
 * @param <Model> the type of the model entity managed by the repository
 */
public interface BaseRepository<Model> {
    /**
     * Retrieves all entities from the repository.
     *
     * @return a list of all entities
     */
    List<Model> findAll();

    /**
     * Saves the given entity into the repository.
     *
     * @param model the entity to be saved
     */
    void save(Model model);

    /**
     * Deletes the given entity from the repository.
     *
     * @param model the entity to be deleted
     */
    void delete(Model model);
}
