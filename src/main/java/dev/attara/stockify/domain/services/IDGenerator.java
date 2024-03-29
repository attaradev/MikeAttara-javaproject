package dev.attara.stockify.domain.services;

/**
 * Interface for generating unique identifiers.
 */
public interface IDGenerator {

    /**
     * Generates a unique identifier.
     *
     * @return A string representing the generated identifier.
     */
    String generateID();
}
