package com.lms.project.configure;
import org.springframework.stereotype.Component;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
@Component
public class PasswordEncryption {
    private static final String ALGORITHM = "AES";
    private static final String ALGORITHM_MODE = "AES/CBC/PKCS5Padding";
    private static final String SECRET_KEY = "1234567890123456";
    public String encrypt(String plainPassword) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        byte[] encryptedPassword = cipher.doFinal(plainPassword.getBytes());
        String encryptedText = Base64.getEncoder().encodeToString(encryptedPassword);
        String ivBase64 = Base64.getEncoder().encodeToString(iv);
        return ivBase64 + ":" + encryptedText;
    }
    public String decrypt(String encryptedPassword) throws Exception {
        if (encryptedPassword == null || !encryptedPassword.contains(":")) {
            throw new IllegalArgumentException("Invalid encrypted password format. It should contain both IV and encrypted text.");
        }
        String[] parts = encryptedPassword.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid encrypted password format. Expected format: IV:EncryptedPassword");
        }
        String ivBase64 = parts[0];
        String encryptedText = parts[1];
        byte[] iv = Base64.getDecoder().decode(ivBase64);
        byte[] encryptedPasswordBytes = Base64.getDecoder().decode(encryptedText);
        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        byte[] decryptedBytes = cipher.doFinal(encryptedPasswordBytes);
        return new String(decryptedBytes);
    }
    public String generateNewKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }
}
