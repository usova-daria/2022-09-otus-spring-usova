package com.otus.spring.ui.impl;

import com.otus.spring.model.Question;
import com.otus.spring.service.api.QuestionsService;
import com.otus.spring.ui.api.ApplicationRunner;

import java.util.List;

public class ConsoleApplicationRunner implements ApplicationRunner {

    private final QuestionsService questionsService;
    private final QuestionsFormatter questionsFormatter;

    public ConsoleApplicationRunner(QuestionsService questionsService, QuestionsFormatter questionsFormatter) {
        this.questionsService = questionsService;
        this.questionsFormatter = questionsFormatter;
    }

    @Override
    public void run() {
        try {
            List<Question> questions = questionsService.getQuestions();
            questions.stream()
                    .map(questionsFormatter::format)
                    .forEach(System.out::println);
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println("Unexpected exception. Exit program");
        }
    }

}
