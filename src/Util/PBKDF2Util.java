package Util;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PBKDF2Util {

    private static final int ITERATIONS = 10000; // 迭代次数
    private static final int KEY_LENGTH = 255; // 密钥长度

    // 生成盐（从用户名派生）
    public static String generateSaltFromUsername(String username) {
        // 简单地使用用户名作为盐值（可以增加复杂度）
        return Base64.getEncoder().encodeToString(username.getBytes());
    }

    // 生成哈希密码
    public static String hashPassword(String password, String username) {
        String salt = generateSaltFromUsername(username);
        try {
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), Base64.getDecoder().decode(salt), ITERATIONS,
                    KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error while hashing password: " + e.getMessage(), e);
        }
    }

    // 验证明文密码与存储的哈希密码是否匹配
    public static boolean verifyPassword(String password, String username, String hashedPassword) {
        String newHash = hashPassword(password, username);
        return newHash.equals(hashedPassword);
    }
}
