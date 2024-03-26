package dev.attara.stockify.application.dto.output;

public record ProductRecord(

        Long id,

        String name,

        int stock,

        double price

) { }
