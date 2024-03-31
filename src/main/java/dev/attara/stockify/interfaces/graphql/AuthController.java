package dev.attara.stockify.interfaces.graphql;

import dev.attara.stockify.application.services.authentication.Authentication;
import dev.attara.stockify.application.services.authentication.AuthenticationHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import javax.naming.AuthenticationException;

/**
 * Controller class responsible for handling GraphQL mutations related to authentication.
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationHandler authenticationHandler;

    /**
     * Authenticates a user and returns an access token.
     *
     * @param email    The email of the user to authenticate.
     * @param password The password of the user to authenticate.
     * @return An access token if authentication is successful.
     * @throws AuthenticationException If authentication fails.
     */
    @MutationMapping
    public String accessToken(@Argument String email, @Argument String password) throws AuthenticationException {
        try {
            String accessToken = authenticationHandler.handle(new Authentication(email, password));
            log.info("Authentication successful for email: {}", email);
            return accessToken;
        } catch (AuthenticationException e) {
            log.error("Authentication failed for email: {}", email, e);
            throw e;
        } catch (Exception e) {
            log.error("An unexpected error occurred during authentication for email: {}", email, e);
            throw new AuthenticationException("Authentication failed");
        }
    }
}
