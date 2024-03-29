package dev.attara.stockify.application.dtos;

import java.util.List;

/**
 * A record representing an order, including its ID, associated user, and product lines.
 */
public record OrderRecord(
        String id,

        UserRecord user,

        List<ProductLineRecord> productLines
) { }
