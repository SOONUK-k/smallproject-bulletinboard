package com.example.bulletinboard.answer.repository;

import com.example.bulletinboard.answer.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer,Long> {
}
