package com.example.bulletinboard.question.dto;

import com.example.bulletinboard.question.entity.QuestionScopeStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

public class QuestionDto {


    @Getter
    @AllArgsConstructor
    public static class Post {

        @NotBlank
        private String title;

        @NotBlank
        private String content;

        @NotBlank
        private QuestionScopeStatus questionScopeStatus;
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {

        @NotBlank
        private Long id;

        @NotBlank
        private String title;

        @NotBlank
        private String content;

        @NotBlank
        private QuestionScopeStatus questionScopeStatus;


    }

}

