package dev.attara.stockify.application.service.impl;

import dev.attara.stockify.application.security.JwtTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenService jwtTokenService;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        // Initialize mocks or perform any additional setup
    }

    @Test
    void testAuthenticate_AuthenticationException() {
        String email = "test@example.com";
        String password = "password";

        when(authenticationManager.authenticate(any()))
                .thenThrow(new AuthenticationException("Authentication failed") {
                    // This is an anonymous subclass of AuthenticationException
                    // You can override any necessary methods here
                });

        assertThrows(AuthenticationException.class, () -> {
            authService.authenticate(email, password);
        });
    }

}
