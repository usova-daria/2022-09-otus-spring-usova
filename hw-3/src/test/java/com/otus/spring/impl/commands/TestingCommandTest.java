package com.otus.spring.impl.commands;

import com.otus.spring.config.ContentConfig;
import com.otus.spring.impl.TestPrinter;
import com.otus.spring.model.Answer;
import com.otus.spring.model.Question;
import com.otus.spring.model.TestReport;
import com.otus.spring.service.api.QuestionsService;
import com.otus.spring.ui.api.AnswersParser;
import com.otus.spring.ui.api.QuestionsFormatter;
import com.otus.spring.ui.api.Reader;
import com.otus.spring.ui.api.ResourceBundleHolder;
import com.otus.spring.ui.impl.InputFormatException;
import com.otus.spring.ui.impl.ResourceBundleHolderImpl;
import com.otus.spring.ui.impl.commands.TestingCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import static com.otus.spring.ui.impl.ConfigurationConstants.ANSWERS_SEPARATOR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestingCommandTest {

    @Mock
    Reader reader;

    @Mock
    QuestionsService questionsService;

    @Mock
    AnswersParser answersParser;

    QuestionsFormatter questionsFormatter;
    TestPrinter printer;
    ResourceBundleHolder resourceBundleHolder;
    TestingCommand command;

    @BeforeEach
    void init() {
        var contentConfig = new ContentConfig();
        contentConfig.setResourceBundleName("messages");
        contentConfig.setLanguage("ru");
        contentConfig.setCountry("RU");

        printer = new TestPrinter();
        resourceBundleHolder = new ResourceBundleHolderImpl(contentConfig);
        questionsFormatter = Question::getText;
        command = new TestingCommand(questionsFormatter, printer, reader, questionsService, answersParser, resourceBundleHolder);
    }

    private Question createQuestion() {
        var answers = new LinkedHashMap<Answer, Boolean>();
        answers.put(new Answer("first answer"), true);
        answers.put(new Answer("second answer"), false);
        return new Question("Question?", answers);
    }

    @Test
    void happyCase_Test() {
        var credentials = new TestReport.Credentials("first name", "last name");
        when(questionsService.getQuestions()).thenReturn(List.of( createQuestion() ));
        when(reader.read()).thenReturn("1");
        when(answersParser.parseAnswers(anyString(), anyInt())).thenReturn(Set.of(1));

        command.run(credentials);

        assertTrue(printer.getPrinted().contains("Question?"));
        assertTrue(printer.getPrinted().contains("answer-value"));

        var report = command.getResult();
        assertEquals(1, report.getResults().getTotalNumberOfQuestions());
        assertEquals(1, report.getResults().getNumberOfCorrectAnswers());
    }

    @Test
    void invalidArguments_Test() {
        assertThrows(IllegalArgumentException.class, () -> command.run(null));
        assertThrows(IllegalArgumentException.class, () -> command.run(new Object()));
    }

    @Test
    void invalidAnswerFormat_Test() {
        var credentials = new TestReport.Credentials("first name", "last name");
        when(questionsService.getQuestions()).thenReturn(List.of( createQuestion() ));
        when(reader.read())
                .thenReturn("abc")
                .thenReturn("1");
        when(answersParser.parseAnswers(anyString(), anyInt()))
                .thenThrow(new InputFormatException( InputFormatException.Format.DIGITS_WITH_SEPARATOR, List.of(ANSWERS_SEPARATOR) ))
                .thenReturn(Set.of(1));

        command.run(credentials);

        assertTrue(printer.getPrinted().contains("Question?"));
        assertEquals(2, StringUtils.countOccurrencesOf(printer.getPrinted(), "answer-value"));
        assertTrue(printer.getPrinted().contains("format-only-digits-with-separator-value-" + ANSWERS_SEPARATOR));

        var report = command.getResult();
        assertEquals(1, report.getResults().getTotalNumberOfQuestions());
        assertEquals(1, report.getResults().getNumberOfCorrectAnswers());
    }

    @Test
    void answerIsOutOfRange_Test() {
        var credentials = new TestReport.Credentials("first name", "last name");
        when(questionsService.getQuestions()).thenReturn(List.of( createQuestion() ));
        when(reader.read())
                .thenReturn("900")
                .thenReturn("1");
        when(answersParser.parseAnswers(anyString(), anyInt()))
                .thenThrow(new InputFormatException( InputFormatException.Format.DIGITS_WITHIN_RANGE, List.of(1, 2) ))
                .thenReturn(Set.of(1));

        command.run(credentials);

        assertTrue(printer.getPrinted().contains("Question?"));
        assertEquals(2, StringUtils.countOccurrencesOf(printer.getPrinted(), "answer-value"));
        assertTrue(printer.getPrinted().contains("format-digits-within-range-value-1-2"));

        var report = command.getResult();
        assertEquals(1, report.getResults().getTotalNumberOfQuestions());
        assertEquals(1, report.getResults().getNumberOfCorrectAnswers());
    }

    @Test
    void testGetResult_CommandIsNotExecuted() {
        assertThrows(IllegalStateException.class, () -> command.getResult());
    }

}