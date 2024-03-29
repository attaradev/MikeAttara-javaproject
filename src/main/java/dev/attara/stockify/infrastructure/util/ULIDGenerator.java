package dev.attara.stockify.infrastructure.util;

import de.huxhorn.sulky.ulid.ULID;
import dev.attara.stockify.domain.services.OrderManager;
import org.springframework.stereotype.Component;

/**
 * ULIDGenerator is an implementation of the IDGenerator interface
 * that generates unique identifiers using ULID (Universally Unique Lexicographically Sortable Identifier).
 */
@Component
public class ULIDGenerator implements OrderManager.IDGenerator {

    private final ULID ulidGenerator = new ULID();

    /**
     * Generates a unique identifier using ULID.
     *
     * @return A string representing the generated ULID.
     */
    @Override
    public String generateID() {
        return ulidGenerator.nextULID();
    }
}
