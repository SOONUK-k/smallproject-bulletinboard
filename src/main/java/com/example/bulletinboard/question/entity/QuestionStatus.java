package com.example.bulletinboard.question.entity;

import lombok.Getter;

@Getter
public enum QuestionStatus {
    QUESTION_REGISTERED("질문 등록 상태"),
    QUESTION_ANSWERED("답변 완료 상태"),
    QUESTION_DELETED("질문 삭제 상태"),
    QUESTION_NONACTIVE("질문 비활성화 상태");

    private String status;

     QuestionStatus(String status) {
        this.status = status;
    }
}
