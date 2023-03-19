package com.example.bulletinboard.answer.entity;

import com.example.bulletinboard.entity.BaseTimeEntity;
import com.example.bulletinboard.question.entity.Question;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
public class Answer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ANSWER_ID")
    private Long id;

    private String title;

    private String content;

    @Setter
    @OneToOne(mappedBy = "answer")
    private Question question;

}
