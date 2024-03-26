package dev.attara.stockify.application.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

/**
 * Utility class for validating email addresses.
 */
@Slf4j
public final class EmailValidator {

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
