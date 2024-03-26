package dev.attara.stockify.application.security;

import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

/**
 * Provides a locally generated RSA key pair.
 */
@Component
public class LocalRSAKeyProvider implements RSAKeyProvider {

    private final RSAKey rsaKey;

    /**
     * Constructs a LocalRSAKeyProvider and generates an RSA key pair.
     *
     * @throws NoSuchAlgorithmException if the specified algorithm for KeyPairGenerator instantiation is invalid
     */
    public LocalRSAKeyProvider() throws NoSuchAlgorithmException {
        KeyPairGenerator kg = KeyPairGenerator.getInstance("RSA");
        kg.initialize(2048);
        KeyPair kp = kg.generateKeyPair();

        RSAPublicKey publicKey = (RSAPublicKey) kp.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) kp.getPrivate();

        rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();

    }

    /**
     * Retrieves the RSA key pair.
     *
     * @return the RSA key pair
     */
    @Override
    public RSAKey getRSAKey() {
        return this.rsaKey;
    }

}
