package com.otus.spring.ui.impl;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AnswersParserTest {

    private final AnswersParser parser = new AnswersParser();

    private static Stream<Arguments> happyData() {
        return Stream.of(
                Arguments.of("1", Set.of(1)),
                Arguments.of("1,2", Set.of(1, 2)),
                Arguments.of("1 , 2", Set.of(1, 2))
        );
    }

    @ParameterizedTest
    @MethodSource("happyData")
    void happyCase_Test(String input, Set<Integer> expected) {
        int maxIndex = 3;
        Set<Integer> actual = parser.parseAnswers(input, maxIndex);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"0", "1-2", "4", "1,4"})
    void unhappyCase_Test(String input) {
        assertThrows(InputFormatException.class, () -> parser.parseAnswers(input, 3));
    }


}