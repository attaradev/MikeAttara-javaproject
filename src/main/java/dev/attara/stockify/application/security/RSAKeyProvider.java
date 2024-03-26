package dev.attara.stockify.application.security;


import com.nimbusds.jose.jwk.RSAKey;

public interface RSAKeyProvider {

    RSAKey getRSAKey();

}
