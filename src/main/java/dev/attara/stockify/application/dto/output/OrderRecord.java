package dev.attara.stockify.application.dto.output;

import java.util.List;

public record OrderRecord(
        Long id,
        List<ProductLineRecord> products,
        UserRecord user
) {
}
