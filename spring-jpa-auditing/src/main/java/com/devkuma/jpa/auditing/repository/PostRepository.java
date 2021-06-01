package com.devkuma.jpa.auditing.repository;

import com.devkuma.jpa.auditing.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, String> {
}
