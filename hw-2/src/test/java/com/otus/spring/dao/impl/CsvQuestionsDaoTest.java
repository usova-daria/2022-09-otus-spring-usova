package com.otus.spring.dao.impl;

import com.otus.spring.dao.mapper.QuestionMapper;
import com.otus.spring.dao.validator.QuestionValidator;
import com.otus.spring.exception.TechnicalException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvQuestionsDaoTest {

    private final CsvQuestionsDao csvQuestionsDao = new CsvQuestionsDao(
            "test-questions.csv",
            new CsvRowParser(),
            new QuestionMapper(new QuestionValidator())
    );

    @Test
    void testReadQuestions() {
        var question = csvQuestionsDao.readQuestions().get(0);
        assertEquals("First question", question.getText());
        assertEquals("first answer", question.getAnswers().get(0).getText() );
        assertEquals("second answer", question.getAnswers().get(1).getText() );
        assertFalse( question.isCorrect( List.of(question.getAnswers().get(0)) ) );
        assertTrue( question.isCorrect( List.of(question.getAnswers().get(1)) ) );
    }

    @Test
    void testReadQuestions_noFile() {
        CsvQuestionsDao csvQuestionsDaoWithNoFile = new CsvQuestionsDao(
                "do-not-exist.csv",
                new CsvRowParser(),
                new QuestionMapper(new QuestionValidator())
        );
        assertThrows(TechnicalException.class, csvQuestionsDaoWithNoFile::readQuestions);
    }

}