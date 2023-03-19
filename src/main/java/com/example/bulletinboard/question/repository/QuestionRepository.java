package com.example.bulletinboard.question.repository;

import com.example.bulletinboard.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
