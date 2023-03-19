package com.example.bulletinboard.question.mapper;

import com.example.bulletinboard.question.dto.QuestionDto;
import com.example.bulletinboard.question.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuestionMapper {


    Question questionPostToQuestion(QuestionDto.Post requestBody);
}
