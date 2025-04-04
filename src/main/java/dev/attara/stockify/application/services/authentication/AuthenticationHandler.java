package dev.attara.stockify.application.services.authentication;

import dev.attara.stockify.application.services.ServiceHandler;
import dev.attara.stockify.infrastructure.security.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

/**
 * Handles user authentication requests.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationHandler implements ServiceHandler<Authentication, String> {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenService jwtTokenService;

    /**
     * Handles user authentication.
     *
     * @param auth The authentication credentials.
     * @return The JWT token upon successful authentication.
     * @throws AuthenticationException If authentication fails.
     * @throws javax.naming.AuthenticationException If authentication fails.
     */
    @Override
    public String handle(Authentication auth) throws AuthenticationException, javax.naming.AuthenticationException {
        String email = auth.email();
        String password = auth.password();
        org.springframework.security.core.Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        return jwtTokenService.generateToken(authentication);
    }

}
