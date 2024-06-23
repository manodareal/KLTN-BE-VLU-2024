package com.example.usermanagement.config;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class ShortUUIDGenerator {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();

    public static String generateShortUUID() {
        // Lấy thời gian hiện tại
        LocalDateTime now = LocalDateTime.now();
        // Định dạng thời gian thành chuỗi
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedNow = now.format(formatter);

        // Tạo một số ngẫu nhiên
        byte[] randomBytes = new byte[4]; // 4 bytes = 32 bits
        secureRandom.nextBytes(randomBytes);
        String randomString = base64Encoder.encodeToString(randomBytes);

        // Kết hợp thời gian và số ngẫu nhiên
        return formattedNow + "" + randomString;
    }
}

