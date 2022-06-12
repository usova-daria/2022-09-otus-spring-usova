package com.otus.spring.dao.impl;

import com.otus.spring.exception.ValidationError;
import com.otus.spring.model.Question;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CsvQuestionsParserTest {

    private final CsvQuestionsParser parser = new CsvQuestionsParser();

    @Test
    void parseHappyCase() {
        String input = "  \"Question?\" , \"this is, first, answer\" ,  ,second answer";
        Question output = parser.parse(input);

        assertEquals("Question?", output.getText());
        assertEquals(2, output.getAnswers().size());
        assertEquals("this is, first, answer", output.getAnswers().get(0).getText());
        assertEquals("second answer", output.getAnswers().get(1).getText());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "\"Question?, first, second",
            "\"Question?, \"\" \"first\", second",
            "Question?",
            "Question?, first"
    })
    @NullSource
    void parseNullQuestion(String input) {
        assertThrows(ValidationError.class, () -> parser.parse(input));
    }

}