package com.otus.spring.ui.impl;

import com.otus.spring.model.Answer;
import com.otus.spring.model.Question;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionsFormatterTest {

    private final QuestionsFormatter questionsFormatter = new QuestionsFormatter();

    @Test
    void format() {
        Question question = new Question("Question?",
                List.of(new Answer("First answer"), new Answer("Second answer")));
        String expected = "Question?" + QuestionsFormatter.NEW_LINE +
                "1. First answer" + QuestionsFormatter.NEW_LINE +
                "2. Second answer" + QuestionsFormatter.NEW_LINE;

        assertEquals(expected, questionsFormatter.format(question));
    }

}