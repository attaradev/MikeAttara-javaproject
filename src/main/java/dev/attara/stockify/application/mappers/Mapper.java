package dev.attara.stockify.application.mappers;

/**
 * Generic interface for mapping between domain models, DTOs (Data Transfer Objects), and entities.
 *
 * @param <Model>  the type of the domain model
 * @param <Record> the type of the DTO or record
 * @param <Entity> the type of the entity
 */
public interface Mapper<Model, Record, Entity> {

    /**
     * Maps the given domain model to its corresponding entity.
     *
     * @param model the domain model to map
     * @return the corresponding entity
     */
    Entity mapToEntity(Model model);

    /**
     * Maps the given entity to its corresponding domain model.
     *
     * @param entity the entity to map
     * @return the corresponding domain model
     */
    Model mapToDomain(Entity entity);

    /**
     * Maps the given domain model to its corresponding DTO or record.
     *
     * @param model the domain model to map
     * @return the corresponding DTO or record
     */
    Record mapToRecord(Model model);
}
