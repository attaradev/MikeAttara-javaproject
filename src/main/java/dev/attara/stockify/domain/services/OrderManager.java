package dev.attara.stockify.domain.services;

import dev.attara.stockify.application.services.ordermanagement.createorder.ProductLineData;
import dev.attara.stockify.domain.models.Order;
import dev.attara.stockify.domain.models.ProductLine;
import dev.attara.stockify.domain.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.regex.Pattern;

public interface OrderManager {

    /**
     * Creates a new order with the specified product lines for the given user.
     *
     * @param productLines the list of product lines to be included in the order
     * @param user         the user who is placing the order
     * @return the created order
     */
    Order createOrder(List<ProductLine> productLines, User user);

    /**
     * Updates an existing order with the specified product lines.
     *
     * @param orderId      the ID of the order to be updated
     * @param productLines the list of product lines to be updated in the order
     * @return the updated order
     */
    Order updateOrder(String orderId, List<ProductLine> productLines);

    /**
     * Creates a new product line based on the provided product line data.
     *
     * @param data the data for creating the product line
     * @return the created product line
     */
    ProductLine createProductLine(ProductLineData data);

    /**
     * Utility class for validating email addresses.
     */
    @Slf4j
    final class EmailValidator {

        /**
         * Regular expression pattern for validating email addresses.
         */
        private static final Pattern EMAIL_PATTERN = Pattern.compile(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

        /**
         * Validates the provided email address.
         *
         * @param email the email address to validate
         * @return true if the email address is not valid, false otherwise
         */
        public static boolean isNotValid(String email) {
            if (!StringUtils.hasText(email)) {
                return true;
            }
            return !EMAIL_PATTERN.matcher(email).matches();
        }
    }

    /**
     * Interface for generating unique identifiers.
     */
    interface IDGenerator {

        /**
         * Generates a unique identifier.
         *
         * @return A string representing the generated identifier.
         */
        String generateID();
    }
}
