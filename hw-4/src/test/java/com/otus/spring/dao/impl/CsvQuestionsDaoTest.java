package com.otus.spring.dao.impl;

import com.otus.spring.config.ContentConfiguration;
import com.otus.spring.dao.mapper.QuestionMapper;
import com.otus.spring.exception.TechnicalException;
import com.otus.spring.model.Answer;
import com.otus.spring.model.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CsvQuestionsDaoTest {

    CsvQuestionsDao csvQuestionsDao;

    @Mock
    QuestionMapper questionMapper;

    @Mock
    CsvRowParser csvRowParser;

    @BeforeEach
    void init() {
        csvQuestionsDao = createDao("test-questions");
    }

    private CsvQuestionsDao createDao(String questionFile) {
        var contentConfig = new ContentConfiguration();
        contentConfig.setLanguage("en");
        contentConfig.setCountry("US");
        contentConfig.setDefaultMessage("TBD");

        return new CsvQuestionsDao(
                questionFile, contentConfig,
                csvRowParser,
                questionMapper
        );
    }

    @Test
    void testReadQuestions() {
        var answersMap = new LinkedHashMap<Answer, Boolean>();
        answersMap.put(new Answer("first answer"), false);
        answersMap.put(new Answer("second answer"), true);
        when(csvRowParser.parse("\"First question\", first answer, second answer, 1"))
                .thenReturn(List.of("First question", "first answer", "second answer", "1"));
        when(questionMapper.map(List.of("First question", "first answer", "second answer", "1")))
                .thenReturn(new Question("First question", answersMap));

        var question = csvQuestionsDao.readQuestions().get(0);
        assertEquals("First question", question.getText());
        assertEquals("first answer", question.getAnswers().get(0).getText() );
        assertEquals("second answer", question.getAnswers().get(1).getText() );
        assertFalse( question.isCorrect( List.of(question.getAnswers().get(0)) ) );
        assertTrue( question.isCorrect( List.of(question.getAnswers().get(1)) ) );
    }

    @Test
    void testReadQuestions_noFile() {
        CsvQuestionsDao csvQuestionsDaoWithNoFile = createDao("do-not-exist");
        assertThrows(TechnicalException.class, csvQuestionsDaoWithNoFile::readQuestions);
    }

}