package com.otus.spring.ui.impl.commands;

import com.otus.spring.model.TestReport;
import com.otus.spring.ui.api.Printer;
import com.otus.spring.ui.api.ResourceBundleHolder;
import com.otus.spring.ui.api.commands.Command;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Order(4)
@Component
public class PrintResultsCommand implements Command {

    private final Printer printer;
    private final ResourceBundleHolder resourceBundleHolder;

    public PrintResultsCommand(Printer printer, ResourceBundleHolder resourceBundleHolder) {
        this.printer = printer;
        this.resourceBundleHolder = resourceBundleHolder;
    }

    @Override
    public void run(Object input) {
        if ( !(input instanceof TestReport) ) {
            throw new IllegalArgumentException("expected input is TestReport");
        }
        TestReport report = (TestReport) input;
        String resultsPattern = resourceBundleHolder.getBundle().getString("results");
        String resultsMessage = MessageFormat.format(resultsPattern,
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
