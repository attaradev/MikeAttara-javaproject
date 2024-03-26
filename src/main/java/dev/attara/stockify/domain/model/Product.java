package dev.attara.stockify.domain.model;

import dev.attara.stockify.domain.exception.InsufficientStockException;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
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

    /**
     * Creates a new product.
     *
     * @param id    the ID of the product
     * @param name  the name of the product
     * @param price the price of the product
     * @param stock the initial stock of the product
     * @return the created product
     * @throws IllegalArgumentException if the ID is invalid, name is null, price is negative, or stock is negative
     */
    public static Product create(long id, @NonNull String name, double price, int stock) throws IllegalArgumentException {
        if (id < 1) throw new IllegalArgumentException("Invalid product ID");
        return new Product(id, validateName(name), validateQuantity(stock), validatePrice(price));
    }

    /**
     * Decreases the stock of the product by the specified quantity.
     *
     * @param quantity the quantity to sell
     * @throws InsufficientStockException if the quantity exceeds the available stock
     */
    public void sell(int quantity) throws InsufficientStockException {
        if (stock < quantity) throw new InsufficientStockException(id);
        stock -= quantity;
    }

    private static int validateQuantity(int quantity) throws IllegalArgumentException {
        if (quantity < 0) throw new IllegalArgumentException("Stock cannot be negative");
        return quantity;
    }

    private static String validateName(String name) {
        if (name == null) throw new IllegalArgumentException("Name cannot be null");
        return name;
    }

    private static double validatePrice(double price) {
        if (price < 0) throw new IllegalArgumentException("Price cannot be negative");
        return price;
    }
}
