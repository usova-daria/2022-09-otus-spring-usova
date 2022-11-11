package com.otus.spring.ui.impl.commands;

import com.otus.spring.model.Answer;
import com.otus.spring.model.Question;
import com.otus.spring.model.TestReport;
import com.otus.spring.service.api.QuestionsService;
import com.otus.spring.ui.api.*;
import com.otus.spring.ui.impl.InputFormatException;
import com.otus.spring.ui.api.InputOutputUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import static com.otus.spring.ui.api.AnswersParser.ANSWERS_SEPARATOR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestingCommandTest {

    @Mock
    Reader reader;

    @Mock
    QuestionsService questionsService;

    @Mock
    AnswersParser answersParser;

    @Mock
    Printer printer;

    @Mock
    MessageSourceHolder messageSourceHolder;

    InputOutputUtils inputOutputUtils;

    QuestionsFormatter questionsFormatter;
    TestingCommand command;

    @BeforeEach
    void init() {
        questionsFormatter = Question::getText;
        inputOutputUtils = (printer, reader, inputMessage) -> reader.read();
        command = new TestingCommand(questionsFormatter, printer, reader, questionsService,
                answersParser, messageSourceHolder, inputOutputUtils);
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

        verify(printer).println("Question?");
        verify(messageSourceHolder).getMessage("answer");

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

        verify(printer).println("Question?");
        verify(messageSourceHolder, times(2)).getMessage("answer");
        verify(messageSourceHolder).getMessage("format.only.digits.with.separator", ANSWERS_SEPARATOR);
        verify(messageSourceHolder).getMessage(eq("wrong.format.exception"), any());

        var report = command.getResult();
        assertEquals(1, report.getResults().getTotalNumberOfQuestions());
        assertEquals(1, report.getResults().getNumberOfCorrectAnswers());
    }

    @Test
    void testGetResult_CommandIsNotExecuted() {
        assertThrows(IllegalStateException.class, () -> command.getResult());
    }

}