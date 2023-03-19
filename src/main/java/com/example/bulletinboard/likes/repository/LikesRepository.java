package com.example.bulletinboard.likes.repository;

import com.example.bulletinboard.likes.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {
}
