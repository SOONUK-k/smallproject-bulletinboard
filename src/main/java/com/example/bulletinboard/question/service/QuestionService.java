package com.example.bulletinboard.question.service;

import com.example.bulletinboard.answer.entity.Answer;
import com.example.bulletinboard.answer.repository.AnswerRepository;
import com.example.bulletinboard.exception.BusinessLogicException;
import com.example.bulletinboard.exception.ExceptionCode;
import com.example.bulletinboard.likes.repository.LikesRepository;
import com.example.bulletinboard.member.entity.Member;
import com.example.bulletinboard.member.repository.MemberRepository;
import com.example.bulletinboard.member.service.MemberService;
import com.example.bulletinboard.question.entity.Question;
import com.example.bulletinboard.question.entity.QuestionScopeStatus;
import com.example.bulletinboard.question.entity.QuestionStatus;
import com.example.bulletinboard.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionService {

    private final MemberRepository memberRepository;

    private final MemberService memberService;
    private final QuestionRepository questionRepository;

//    private final LikesRepository likesRepository;

    private final AnswerRepository answerRepository;

    //Dto->(Mapper)->Entity 형태로 서비스로 전달됨.
    @Transactional
    public Question createQuestion(Long memberId, String title, String content, QuestionScopeStatus questionScopeStatus) {

        //Authentication, Authorization
        //1. Member Exist
        //2. Member is Member
        memberService.verifyExistsId(memberId);
        memberService.verifyMemberIsMember(memberId);

        //Dto -> Entity :During Mapping,
        // it checks whether it has title, content, and questionScope.
        //Logic
        Member foundMember = memberRepository.findById(memberId).get();
        Question madeQuestion = Question.makeQuestion(foundMember, title,content, questionScopeStatus);
        questionRepository.save(madeQuestion);
        return madeQuestion;
    }

    public List findQuestion(Long questionId,Long memberId) {
        //Authentication, Authorization
        //1. Member Exist
        //2. Question Exist
        //3. Question Not Deleted
        memberService.verifyExistsId(memberId);
        verifyExistId(questionId);
        verifyQuestionDelete(questionId);

        //Logic
        //1. SCOPE -> SECRET
        //2. SCOPE -> PUBLIC

        //1.
        Member foundMember = memberRepository.findById(memberId).get();
        Question foundQuestion = questionRepository.findById(questionId).get();

        if(foundQuestion.getQuestionScopeStatus()== QuestionScopeStatus.SECRET){
            if(foundQuestion.getMember().getMemberId() != foundMember.getMemberId()){
                throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_ALLOWED);
            }
        }
        //if it has answer?
        Optional<Answer> foundAnswer = answerRepository.findById(foundQuestion.getAnswer().getId());
        if (foundAnswer.isEmpty()) {
            return List.of(foundQuestion);
        }
        return List.of(foundQuestion, foundAnswer);


    }

    @Transactional
    public Question updateQuestion(Long memberId, Question newQuestion) {

        //Authentication, Authorization
        //1. Member Exist
        //2. Member is Member(Not Admin)
        //3. Question Exist
        //4. Question Not Deleted
        //5. MemberEmail of Question == Member Email
        memberService.verifyExistsId(memberId);
        memberService.verifyMemberIsMember(memberId);
        verifyExistId(newQuestion.getQuestionId());
        verifyQuestionDelete(newQuestion.getQuestionId());
        verifyQuestion(memberId, newQuestion);

        //Logic
        Question foundQuestion = questionRepository.findById(newQuestion.getQuestionId()).get();
        Member member = memberRepository.findById(memberId).get();
        foundQuestion.updateQuestion(member, newQuestion.getTitle(), newQuestion.getContent(), newQuestion.getQuestionScopeStatus());

        //Return
        return questionRepository.save(foundQuestion);
    }

    @Transactional
    public void deleteQuestion(Long memberId, Question question) {
        //Authentication, Authorization
        //1. Member Exist
        //2. Member is Member(Not Admin)
        //3. Question Exist
        //4. Question Not Deleted
        //5. MemberEmail of Question == Member Email
        memberService.verifyExistsId(memberId);
        memberService.verifyMemberIsMember(memberId);
        verifyExistId(question.getQuestionId());
        verifyQuestionDelete(question.getQuestionId());
        verifyQuestion(memberId, question);

        //Logic
        Question foundQuestion = questionRepository.findById(question.getQuestionId()).get();
        foundQuestion.deleteQuestion();

        //Return
        questionRepository.save(foundQuestion);
    }

    private void verifyExistId(Long id) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);

        Question question = optionalQuestion.orElseThrow(()->
                new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
    }

    private void verifyQuestion(Long memberId, Question question) {
        Member foundMember1 = memberRepository.findById(memberId).get();
        Member foundMember2 = questionRepository.findById(question.getQuestionId()).get().getMember();
        if(foundMember1.getMemberId() != foundMember2.getMemberId()){
            new BusinessLogicException(ExceptionCode.MEMBER_NOT_ALLOWED);
        }
    }

    private void verifyQuestionDelete(Long id) {
        Question foundQuestion = questionRepository.findById(id).get();
        if (foundQuestion.getQuestionStatus() == QuestionStatus.QUESTION_DELETED) {
            new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND);
        }

    }
}
