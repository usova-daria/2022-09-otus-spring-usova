package com.otus.spring.ui.impl.commands;

import com.otus.spring.model.Answer;
import com.otus.spring.model.Question;
import com.otus.spring.model.TestReport;
import com.otus.spring.ui.api.MessageSourceHolder;
import com.otus.spring.ui.api.Printer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PrintResultsCommandTest {

    @Mock
    Printer printer;

    @Mock
    MessageSourceHolder messageSourceHolder;

    PrintResultsCommand command;

    @BeforeEach
    void init() {
        command = new PrintResultsCommand(printer, messageSourceHolder);
    }

    private Question createQuestion() {
        var answers = new LinkedHashMap<Answer, Boolean>();
        answers.put(new Answer("first answer"), true);
        answers.put(new Answer("second answer"), false);
        return new Question("Question?", answers);
    }

    @Test
    void invalidArguments_Test() {
        assertThrows(IllegalArgumentException.class, () -> command.run(null));
        assertThrows(IllegalArgumentException.class, () -> command.run(new Object()));
    }

    @Test
    void happyCase_Test() {
        TestReport report = new TestReport(new TestReport.Credentials("username"));
        var question = createQuestion();
        report.addResult(question, List.of(question.getAnswers().get(0)));

        command.run(report);

        verify(messageSourceHolder).getMessage("results", "username", 1, 1);
        assertNull(command.getResult());
    }

}