package dev.attara.stockify.application.services;

/**
 * Interface for service handlers.
 *
 * @param <Service>  The type of the service request.
 * @param <Response> The type of the response produced by the handler.
 */
public interface ServiceHandler<Service, Response> {
    /**
     * Handles the service request.
     *
     * @param service The service request to handle.
     * @return The response produced by handling the service request.
     * @throws Exception If an error occurs during the handling process.
     */
    Response handle(Service service) throws Exception;
}
