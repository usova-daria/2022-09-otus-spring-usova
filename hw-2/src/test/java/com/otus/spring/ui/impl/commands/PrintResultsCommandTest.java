package com.otus.spring.ui.impl.commands;

import com.otus.spring.model.Answer;
import com.otus.spring.model.Question;
import com.otus.spring.model.TestReport;
import com.otus.spring.ui.api.ResourceBundleHolder;
import com.otus.spring.ui.impl.ResourceBundleHolderImpl;
import com.otus.spring.ui.impl.TestPrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PrintResultsCommandTest {

    TestPrinter printer;

    ResourceBundleHolder resourceBundleHolder;

    PrintResultsCommand command;

    @BeforeEach
    void init() {
        printer = new TestPrinter();
        resourceBundleHolder = new ResourceBundleHolderImpl("messages", "ru", "RU");
        command = new PrintResultsCommand(printer, resourceBundleHolder);
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
        TestReport report = new TestReport(new TestReport.Credentials("first name", "last name"));
        var question = createQuestion();
        report.addResult(question, List.of(question.getAnswers().get(0)));

        command.run(report);

        assertTrue(printer.getPrinted().contains("first name last name-1-1"));
        assertNull(command.getResult());
    }

}