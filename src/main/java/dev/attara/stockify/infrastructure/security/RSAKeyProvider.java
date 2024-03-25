package dev.attara.stockify.infrastructure.security;


import com.nimbusds.jose.jwk.RSAKey;

public interface RSAKeyProvider {
    RSAKey getRSAKey();
}
