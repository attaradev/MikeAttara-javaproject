package dev.attara.stockify.application.dto.input;

import java.util.List;

public record CreateOrderDTO(Long userId, List<ProductLineDTO> productLines) {
}
