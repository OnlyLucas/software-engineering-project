package com.example.software_engineering_project.util;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;
import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Signature;


public class PasswordHashUtil {
    private static final String TAG = "PasswordHashUtil";
    private static final String KEYSTORE_ALIAS = "password_hash_key";

    public static String hashPassword(String password) {
        try {
            // Generate a key pair for signing
            KeyPair keyPair = generateKeyPair();

            // Sign the password using the private key
            byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
            byte[] signature = signData(passwordBytes, keyPair.getPrivate());

            // Encode the signature as a Base64 string
            String hashedPassword = Base64.encodeToString(signature, Base64.DEFAULT);
            return hashedPassword;
        } catch (Exception e) {
            Log.e(TAG, "Error hashing password", e);
            return null;
        }
    }

    private static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_EC, "AndroidKeyStore");

        KeyGenParameterSpec keyGenParameterSpec = new KeyGenParameterSpec.Builder(
                KEYSTORE_ALIAS,
                KeyProperties.PURPOSE_SIGN)
                .setAlgorithmParameterSpec(new java.security.spec.ECGenParameterSpec("secp256r1"))
                .setDigests(KeyProperties.DIGEST_SHA256,
                        KeyProperties.DIGEST_SHA512)
                .setUserAuthenticationRequired(false)
                .build();

        keyPairGenerator.initialize(keyGenParameterSpec);
        return keyPairGenerator.generateKeyPair();
    }

    private static byte[] signData(byte[] data, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withECDSA");
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }
}
