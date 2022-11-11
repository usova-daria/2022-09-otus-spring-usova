package com.otus.spring.ui.impl.commands;

import com.otus.spring.model.TestReport;
import com.otus.spring.ui.api.MessageSourceHolder;
import com.otus.spring.ui.api.Printer;
import com.otus.spring.ui.api.Reader;
import com.otus.spring.ui.api.commands.Command;
import com.otus.spring.ui.api.InputOutputUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Component
public class GetCredentialsCommand implements Command {

    private final Printer printer;

    private final Reader reader;

    private final MessageSourceHolder messageSourceHolder;

    private final InputOutputUtils inputOutputUtils;

    private TestReport.Credentials credentials;


    public GetCredentialsCommand(Printer printer, Reader reader,
                                 MessageSourceHolder messageSourceHolder,
                                 InputOutputUtils inputOutputUtils) {
        this.printer = printer;
        this.reader = reader;
        this.messageSourceHolder = messageSourceHolder;
        this.inputOutputUtils = inputOutputUtils;
    }

    @Override
    public void run(Object input) {
        String username = inputOutputUtils.readNotBlankInput(printer, reader,
                messageSourceHolder.getMessage("username"));
        printer.printBlankLine();
        credentials = new TestReport.Credentials(username);
    }

    @Override
    public TestReport.Credentials getResult() {
        if (credentials == null) {
            throw new IllegalStateException("the command should be executed first");
        }
        return credentials;
    }

}
