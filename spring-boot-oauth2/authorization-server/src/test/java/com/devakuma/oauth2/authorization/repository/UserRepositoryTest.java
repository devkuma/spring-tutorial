package com.devakuma.oauth2.authorization.repository;

import com.devakuma.oauth2.authorization.dto.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertNewUser() {
        userRepository.save(User.builder()
                .uid("devkuma.com@gmail.com")
                .password(passwordEncoder.encode("1234"))
                .name("devkuma")
                .roles(Collections.singletonList("ROLE_USER"))
                .build());
    }
}
