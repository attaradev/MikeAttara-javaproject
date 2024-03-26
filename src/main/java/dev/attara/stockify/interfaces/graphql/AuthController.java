package dev.attara.stockify.interfaces.graphql;

import dev.attara.stockify.application.service.authentication.Authentication;
import dev.attara.stockify.application.service.authentication.AuthenticationHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import javax.naming.AuthenticationException;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationHandler authenticationHandler;

    @MutationMapping
    public String accessToken(@Argument String email, @Argument String password) throws AuthenticationException {
        try {
            return authenticationHandler.handle(new Authentication(email, password));
        } catch (AuthenticationException e) {
            logger.error("Authentication failed for email: {}", email, e);
            throw e;
        } catch (Exception e) {
            logger.error("An unexpected error occurred during authentication for email: {}", email, e);
            throw new AuthenticationException("Authentication failed");
        }
    }
}
