package com.otus.spring.ui.api;

import java.util.Set;

public interface AnswersParser {

    Set<Integer> parseAnswers(String input, int maxIndex);

}
