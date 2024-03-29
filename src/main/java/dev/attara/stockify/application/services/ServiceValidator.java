package dev.attara.stockify.application.services;

/**
 * Interface for service validators.
 *
 * @param <Service> The type of the service request to validate.
 */
public interface ServiceValidator<Service> {
    /**
     * Validates the service request.
     *
     * @param service The service request to validate.
     * @throws Exception If the validation fails.
     */
    void validate(Service service) throws Exception;
}
