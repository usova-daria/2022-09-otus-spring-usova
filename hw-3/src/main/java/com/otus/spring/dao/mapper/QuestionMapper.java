package com.otus.spring.dao.mapper;

import com.otus.spring.model.Question;

import java.util.List;

public interface QuestionMapper {

    String CORRECT_ANSWER_SEPARATOR = "-";

    Question map(List<String> entries);

}
