package com.otus.spring.ui.impl.commands;

import com.otus.spring.model.Answer;
import com.otus.spring.model.Question;
import com.otus.spring.model.TestReport;
import com.otus.spring.service.api.QuestionsService;
import com.otus.spring.ui.api.*;
import com.otus.spring.ui.api.commands.Command;
import com.otus.spring.ui.impl.InputFormatException;
import com.otus.spring.ui.impl.InputOutputUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Order(3)
@Component
public class TestingCommand implements Command {

    private final QuestionsFormatter questionsFormatter;
    private final Printer printer;
    private final Reader reader;
    private final QuestionsService questionsService;
    private final AnswersParser answersParser;
    private final ResourceBundleHolder resourceBundleHolder;

    private TestReport report;

    public TestingCommand(QuestionsFormatter questionsFormatter,
                          Printer printer,
                          Reader reader,
                          QuestionsService questionsService,
                          AnswersParser answersParser,
                          ResourceBundleHolder resourceBundleHolder) {
        this.questionsFormatter = questionsFormatter;
        this.printer = printer;
        this.reader = reader;
        this.questionsService = questionsService;
        this.answersParser = answersParser;
        this.resourceBundleHolder = resourceBundleHolder;
    }

    @Override
    public void run(Object input) {
        if ( !(input instanceof TestReport.Credentials) ) {
            throw new IllegalArgumentException("expected input is TestReport.Credentials");
        }
        TestReport.Credentials credentials = (TestReport.Credentials) input;
        report = new TestReport(credentials);

        List<Question> questions = questionsService.getQuestions();
        questions.forEach(q -> askOneQuestionAndFillInReport(q, report));
    }

    @Override
    public TestReport getResult() {
        if (report == null) {
            throw new IllegalStateException("the command should be executed first");
        }
        return report;
    }

    private void askOneQuestionAndFillInReport(Question question, TestReport testReport) {
        printer.println( questionsFormatter.format(question) );
        List<Answer> answers = readAnswers(question);
        testReport.addResult(question, answers);
        printer.printBlankLine();
    }

    private List<Answer> readAnswers(Question question) {
        try {
            String answer = InputOutputUtils.readNotBlankInput(printer, reader,
                    resourceBundleHolder.getBundle().getString("answer"));
            List<Answer> allAnswers = question.getAnswers();
            Set<Integer> userAnswers = answersParser.parseAnswers(answer, allAnswers.size());
            return userAnswers.stream()
                    .map(i -> allAnswers.get(i - 1))
                    .collect(Collectors.toList());
        } catch (InputFormatException e) {
            String correctFormat = MessageFormat.format(
                    resourceBundleHolder.getBundle().getString(e.getInvalidFormat().getMessageKey()),
                    e.getParams().toArray()
            );
            String pattern = resourceBundleHolder.getBundle().getString("wrong.format.exception");
            printer.println( MessageFormat.format(pattern, correctFormat) );
            return readAnswers(question);
        }
    }

}
