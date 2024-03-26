package dev.attara.stockify.application.dto;

import java.util.List;

/**
 * A record representing an order, including its ID, associated user, and product lines.
 */
public record OrderRecord(
        long id,

        UserRecord user,

        List<ProductLineRecord> productLines
) { }
