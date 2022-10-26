package com.otus.spring.ui.impl.commands;

import com.otus.spring.model.TestReport;
import com.otus.spring.ui.api.MessageSourceHolder;
import com.otus.spring.ui.api.Printer;
import com.otus.spring.ui.api.Reader;
import com.otus.spring.ui.api.commands.Command;
import com.otus.spring.ui.impl.InputOutputUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Component
public class GetCredentialsCommand implements Command {

    private final Printer printer;

    private final Reader reader;

    private final MessageSourceHolder messageSourceHolder;

    private TestReport.Credentials credentials;

    public GetCredentialsCommand(Printer printer, Reader reader, MessageSourceHolder messageSourceHolder) {
        this.printer = printer;
        this.reader = reader;
        this.messageSourceHolder = messageSourceHolder;
    }

    @Override
    public void run(Object input) {
        String firstName = InputOutputUtils.readNotBlankInput(printer, reader,
                messageSourceHolder.getMessage("first.name"));
        String lastName = InputOutputUtils.readNotBlankInput(printer, reader,
                messageSourceHolder.getMessage("last.name"));
        printer.printBlankLine();
        credentials = new TestReport.Credentials(firstName, lastName);
    }

    @Override
    public TestReport.Credentials getResult() {
        if (credentials == null) {
            throw new IllegalStateException("the command should be executed first");
        }
        return credentials;
    }

}
