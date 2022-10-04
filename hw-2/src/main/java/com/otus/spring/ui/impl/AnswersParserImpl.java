package com.otus.spring.ui.impl;

import com.otus.spring.ui.api.AnswersParser;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class AnswersParserImpl implements AnswersParser {

    private final static String ANSWERS_SEPARATOR = ConfigurationConstants.ANSWERS_SEPARATOR;
    private final static String ANSWER_REGEX = "(\\d+\\s*" + ANSWERS_SEPARATOR + "*\\s*)+";
    private final static Pattern ANSWER_PATTERN = Pattern.compile(ANSWER_REGEX);

    public Set<Integer> parseAnswers(String input, int maxIndex) {
        if (input == null || !ANSWER_PATTERN.matcher(input).matches()) {
            throw new InputFormatException( InputFormatException.Format.DIGITS_WITH_SEPARATOR, List.of(ANSWERS_SEPARATOR) );
        }

        Set<Integer> userAnswers = getCorrectAnswerIndexes(input);
        if (userAnswers.stream().anyMatch(a -> a > maxIndex || a == 0)) {
            throw new InputFormatException( InputFormatException.Format.DIGITS_WITHIN_RANGE, List.of(1, maxIndex) );
        }

        return userAnswers;
    }

    private Set<Integer> getCorrectAnswerIndexes(String answer) {
        return Arrays.stream(answer.split(ANSWERS_SEPARATOR))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
    }

}
