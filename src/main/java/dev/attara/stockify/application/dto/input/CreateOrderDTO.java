package dev.attara.stockify.application.dto.input;

import dev.attara.stockify.domain.model.User;

import java.util.List;

public record CreateOrderDTO(

        List<ProductLineDTO> productLines,

        User user

) { }
