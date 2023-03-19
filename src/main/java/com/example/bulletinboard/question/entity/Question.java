package com.example.bulletinboard.question.entity;

import com.example.bulletinboard.answer.entity.Answer;
import com.example.bulletinboard.entity.BaseEntity;
import com.example.bulletinboard.entity.BaseTimeEntity;
import com.example.bulletinboard.likes.entity.Likes;
import com.example.bulletinboard.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Question extends BaseTimeEntity {

    private Question(String title, String content, QuestionStatus questionStatus, QuestionScopeStatus questionScopeStatus, Member member) {
        this.title = title;
        this.content = content;
        this.questionStatus = questionStatus;
        this.questionScopeStatus = questionScopeStatus;
        this.member = member;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "QUESTION_ID")
    private Long questionId;

    private String title;

    private String content;

    private QuestionStatus questionStatus;

    private QuestionScopeStatus questionScopeStatus;

    //cascade need modify
    //Owner of relationship
    @OneToMany(mappedBy = "question")
    private List<Likes> likesList = new ArrayList<>();

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ANSWER_ID")
    private Answer answer;


    //==연관관계 메서드==//
    //1. question <-> likes
    public void addLikes(Likes likes) {
        likes.setQuestion(this);
        likesList.add(likes);
    }

    //2. question <-> answer
    public void setAnswer(Answer answer) {
        this.answer = answer;
        answer.setQuestion(this);
    }

    //==Method==//
    public int countLikes() {
        return likesList.size();
    }

    public static Question makeQuestion(Member member, String title, String content, QuestionScopeStatus questionScopeStatus) {
        Question question = new Question(title, content, QuestionStatus.QUESTION_REGISTERED, questionScopeStatus, member);
        return question;
    }

    public void updateQuestion(Member member, String title, String content, QuestionScopeStatus questionScopeStatus) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.questionScopeStatus = questionScopeStatus;

    }

    public void deleteQuestion() {
        this.questionStatus = QuestionStatus.QUESTION_DELETED;
    }


}
