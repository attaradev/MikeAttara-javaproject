package dev.attara.stockify.domain.repository;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Base repository interface defining common operations for repositories.
 *
 * @param <Model> the type of the model entity managed by the repository
 */
public interface BaseRepository<Model> {
    AtomicLong atl = new AtomicLong(0);

    /**
     * Generates and returns the next ID for a new entity.
     *
     * @return the next available ID
     */
    default long nextId() {
        return atl.addAndGet(1L);
    }

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
