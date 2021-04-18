package com.devakuma.oauth2.resource.controller;

import com.devakuma.oauth2.resource.dto.entity.User;
import com.devakuma.oauth2.resource.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping(value = "/users")
    public List<User> findAllUser() {
        return userRepository.findAll();
    }
}
