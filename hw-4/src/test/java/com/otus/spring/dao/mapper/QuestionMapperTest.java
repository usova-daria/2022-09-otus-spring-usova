package com.otus.spring.dao.mapper;

import com.otus.spring.dao.validator.QuestionValidator;
import com.otus.spring.model.Answer;
import com.otus.spring.model.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.otus.spring.dao.mapper.QuestionMapper.CORRECT_ANSWER_SEPARATOR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class QuestionMapperTest {

    QuestionMapper questionMapper;

    @Mock
    QuestionValidator questionValidator;

    @BeforeEach
    void init() {
        questionMapper = new QuestionMapperImpl(questionValidator);
    }

    @Test
    void happyCase_questionTextIsCorrect_Test() {
        List<String> entries = List.of("Question?", "First answer","Second answer", "0");
        Question question = questionMapper.map(entries);

        assertEquals("Question?", question.getText());
    }

    @Test
    void happyCase_oneCorrectAnswer_Test() {
        List<String> entries = List.of("Question?", "First answer","Second answer", "0");
        Question question = questionMapper.map(entries);
        List<Answer> answers = question.getAnswers();

        assertEquals(entries.get(1), answers.get(0).getText());

        assertEquals(entries.get(2), answers.get(1).getText());
        assertTrue(question.isCorrect(
                List.of(new Answer("First answer"))
        ));
    }

    @Test
    void happyCase_multiSelect_Test() {
        List<String> entries = List.of("Question?", "First answer", "Second answer", "0" + CORRECT_ANSWER_SEPARATOR + "1");
        Question question = questionMapper.map(entries);
        List<Answer> answers = question.getAnswers();

        assertEquals(entries.get(1), answers.get(0).getText());
        assertEquals(entries.get(2), answers.get(1).getText());
        assertTrue(question.isCorrect(
                List.of(new Answer("First answer"), new Answer("Second answer"))
        ));
    }

}