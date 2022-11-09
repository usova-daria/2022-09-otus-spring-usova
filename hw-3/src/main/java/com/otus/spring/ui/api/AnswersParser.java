package com.otus.spring.ui.api;

import java.util.Set;

public interface AnswersParser {

    String ANSWERS_SEPARATOR = ",";

    Set<Integer> parseAnswers(String input, int maxIndex);

}
