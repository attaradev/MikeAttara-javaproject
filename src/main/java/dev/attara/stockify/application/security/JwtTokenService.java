package dev.attara.stockify.application.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.naming.AuthenticationException;
import java.time.Instant;
import java.util.Collection;

/**
 * Service for generating JWT tokens.
 */
public interface JwtTokenService {

    /**
     * Generates a JWT token based on the provided authentication.
     *
     * @param authentication The authentication object containing user details.
     * @return The generated JWT token.
     * @throws AuthenticationException If an authentication error occurs.
     */
    String generateToken(Authentication authentication) throws AuthenticationException;

    /**
     * Generates a JWT token with the given name, authorities, and expiration time.
     *
     * @param name        The name of the user.
     * @param authorities The authorities granted to the user.
     * @param expiresAt   The expiration time of the token.
     * @return The generated JWT token.
     */
    String generateToken(String name, Collection<? extends GrantedAuthority> authorities, Instant expiresAt);
}
