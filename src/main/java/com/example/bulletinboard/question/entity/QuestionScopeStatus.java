package com.example.bulletinboard.question.entity;

public enum QuestionScopeStatus {
    PUBLIC("공개글 상태"),
    SECRET("비밀글 상태");

    public String status;

    QuestionScopeStatus(String status) {
        this.status = status;
    }
}
