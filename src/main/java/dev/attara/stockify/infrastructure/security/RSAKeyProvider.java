package dev.attara.stockify.infrastructure.security;

import com.nimbusds.jose.jwk.RSAKey;

/**
 * Interface for providing RSA keys.
 */
public interface RSAKeyProvider {

    /**
     * Gets the RSA key.
     *
     * @return the RSA key
     */
    RSAKey getRSAKey();
}
