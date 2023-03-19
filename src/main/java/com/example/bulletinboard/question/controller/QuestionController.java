package com.example.bulletinboard.question.controller;

import com.example.bulletinboard.member.dto.MemberDto;
import com.example.bulletinboard.question.dto.QuestionDto;
import com.example.bulletinboard.question.entity.Question;
import com.example.bulletinboard.question.mapper.QuestionMapper;
import com.example.bulletinboard.question.repository.QuestionRepository;
import com.example.bulletinboard.question.service.QuestionService;
import com.example.bulletinboard.utils.UriCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

//#2. Dto check
@RestController
@RequiredArgsConstructor
@RequestMapping("/v11/questions")
@Validated
public class QuestionController {

    private final static String QUESTION_DEFAULT_STRING = "/v11/questions";

    private final QuestionRepository questionRepository;

    private final QuestionService questionService;

    private final QuestionMapper questionMapper;

    @PostMapping("/{member-id}")
    public ResponseEntity postQuestion(@PathVariable("member-id") @Positive long memberId,
                                       @Valid @RequestBody QuestionDto.Post requestBody) {
        Question question = questionMapper.questionPostToQuestion(requestBody);
        Question madeQuestion = questionService.createQuestion(memberId, question.getTitle(), question.getContent(), question.getQuestionScopeStatus());

        URI location = UriCreator.createUri(QUESTION_DEFAULT_STRING, madeQuestion.getQuestionId());

        return ResponseEntity.created(location).build();
    }


    @GetMapping("/{member-id}/{question-id}")
    public ResponseEntity getQuestion(
            @PathVariable("member-id") @Positive long questionId,
            @PathVariable("question-id") @Positive long memberId) {
        List members = questionService.findQuestion(questionId, memberId);

        return new ResponseEntity<>(members, HttpStatus.OK);
    }

}
