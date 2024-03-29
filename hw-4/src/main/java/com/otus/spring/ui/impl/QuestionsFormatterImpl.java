package com.otus.spring.ui.impl;

import com.otus.spring.model.Question;
import com.otus.spring.ui.api.QuestionsFormatter;
import org.springframework.stereotype.Component;

import static com.otus.spring.ui.impl.console.ConsolePrinter.NEW_LINE;

@Component
public class QuestionsFormatterImpl implements QuestionsFormatter {

    @Override
    public String format(Question question) {
        StringBuilder formattedQuestion = new StringBuilder();
        formattedQuestion.append(question.getText());
        formattedQuestion.append(NEW_LINE);

        for (int i = 0; i < question.getAnswers().size(); i++) {
            formattedQuestion.append(i + 1)
                    .append(". ")
                    .append(question.getAnswers().get(i).getText())
                    .append(NEW_LINE);
        }

        return formattedQuestion.toString();
    }

}
