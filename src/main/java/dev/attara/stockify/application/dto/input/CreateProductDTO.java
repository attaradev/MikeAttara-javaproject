package dev.attara.stockify.application.dto.input;

public record CreateProductDTO(

        String name,

        int stock,

        double price

) { }
