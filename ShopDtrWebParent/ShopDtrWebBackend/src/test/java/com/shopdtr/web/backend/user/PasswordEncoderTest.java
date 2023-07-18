package com.shopdtr.web.backend.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordEncoderTest {

    @Test
    public void testEncodePassword() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "1234567890";
        String encodePassword = passwordEncoder.encode(rawPassword);
        System.out.println(encodePassword);

        boolean matchPassword = passwordEncoder.matches(rawPassword, encodePassword);
        assertTrue(matchPassword);
    }
}
