package com.example.bulletinboard.questionTest;

import com.example.bulletinboard.member.entity.Member;
import com.example.bulletinboard.question.entity.Question;
import com.example.bulletinboard.question.entity.QuestionScopeStatus;
import com.example.bulletinboard.question.repository.QuestionRepository;
import com.example.bulletinboard.question.service.QuestionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional

public class questionCRUD {

    @Autowired
    EntityManager em;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuestionService questionService;



    @Test
    public void questionPost() throws Exception {
        Member madeMember = Member.makeMember("hgd@gmail.com", "홍길동", "010-1234-1234", Member.MemberStatus.MEMBER_ACTIVE, Member.MemberLevel.MEMBER_LEVEL2);
        em.persist(madeMember);

        Question madeQuestion = questionService.createQuestion(madeMember.getMemberId(), "title", "contents", QuestionScopeStatus.PUBLIC);
        em.flush();
        em.clear();

        Question getQuestion = questionRepository.findById(madeQuestion.getQuestionId()).get();
        Assertions.assertEquals(getQuestion.getQuestionId(), madeQuestion.getQuestionId());
    }
}
