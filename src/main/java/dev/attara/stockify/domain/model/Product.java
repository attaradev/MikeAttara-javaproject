package dev.attara.stockify.domain.model;

import dev.attara.stockify.domain.exception.InsufficientStockException;
import lombok.*;

@Getter
public class Product {
    private final long id;
    private String name;
    private int stock;
    private double price;

    private Product(long id, String name, int stock, double price) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
    }

    public static Product create(long id, String name, double price, int stock) throws IllegalArgumentException {
        if (id < 1) throw new IllegalArgumentException("invalid id");
        return new Product(id, validateName(name), validateStock(stock), validatePrice(price));
    }

    public void sell(int quantity) throws InsufficientStockException {
        if (stock < quantity) throw new InsufficientStockException(id);
        stock -= quantity;
    }

    public void setName(String name){
        this.name = validateName(name);
    }

    public void setPrice(double price) {
        this.price = validatePrice(price);
    }

    public void setStock(int stock) {
        this.stock = validateStock(stock);
    }

    private static int validateStock(int price) throws IllegalArgumentException {
        if (price < 0) throw new IllegalArgumentException("stock cannot be negative");
        return price;
    }

    private static String validateName(String name){
        if (name == null) throw new IllegalArgumentException("name required");
        return name;
    }

    private static double validatePrice(double price) {
        if (price < 0) throw new IllegalArgumentException("price cannot be negative");
        return price;
    }
}
