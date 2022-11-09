package com.otus.spring.ui.impl.commands;

import com.otus.spring.model.TestReport;
import com.otus.spring.ui.api.MessageSourceHolder;
import com.otus.spring.ui.api.Printer;
import com.otus.spring.ui.api.commands.Command;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(4)
@Component
public class PrintResultsCommand implements Command {

    private final Printer printer;
    private final MessageSourceHolder messageSourceHolder;

    public PrintResultsCommand(Printer printer, MessageSourceHolder messageSourceHolder) {
        this.printer = printer;
        this.messageSourceHolder = messageSourceHolder;
    }

    @Override
    public void run(Object input) {
        if ( !(input instanceof TestReport) ) {
            throw new IllegalArgumentException("expected input is TestReport");
        }

        TestReport report = (TestReport) input;
        String resultsMessage = messageSourceHolder.getMessage("results",
                report.getCredentials().getFirstName() + " " + report.getCredentials().getLastName(),
                report.getResults().getNumberOfCorrectAnswers(),
                report.getResults().getTotalNumberOfQuestions());
        printer.println(resultsMessage);
    }

    @Override
    public Object getResult() {
        return null;
    }

}
