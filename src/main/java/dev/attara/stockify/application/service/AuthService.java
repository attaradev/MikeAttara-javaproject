package dev.attara.stockify.application.service;

import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    String authenticate(String email, String password) throws AuthenticationException, javax.naming.AuthenticationException;
}
