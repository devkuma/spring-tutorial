package com.devakuma.oauth2.authorization;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
public class EncodingTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void encodeTest() {
        System.out.printf("testSecret : %s", passwordEncoder.encode("testSecret"));
    }
}