package com.devakuma.oauth2.authorization.repository;

import com.devakuma.oauth2.authorization.dto.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUid(String email);
}
