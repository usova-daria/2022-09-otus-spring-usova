package com.otus.spring.impl;

import com.otus.spring.dao.api.QuestionsDao;
import com.otus.spring.model.Answer;
import com.otus.spring.model.Question;
import com.otus.spring.service.impl.QuestionsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionsServiceImplTest {

    @Mock
    QuestionsDao questionsDao;

    private QuestionsServiceImpl questionsService;

    @BeforeEach
    void init() {
        questionsService = new QuestionsServiceImpl(questionsDao);
    }

    @Test
    void testGetQuestions() {
        var questions = List.of(
                new Question("question", Map.of(new Answer("first answer"), true,
                                                    new Answer("second answer"), false))
        );
        when(questionsDao.readQuestions()).thenReturn(questions);
        assertEquals(questions, questionsService.getQuestions());
    }

}