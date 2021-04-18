package com.devakuma.oauth2.resource.repository;


import com.devakuma.oauth2.resource.dto.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
