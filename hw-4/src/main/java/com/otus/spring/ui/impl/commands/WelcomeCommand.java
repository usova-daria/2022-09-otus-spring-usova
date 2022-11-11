package com.otus.spring.ui.impl.commands;

import com.otus.spring.ui.api.MessageSourceHolder;
import com.otus.spring.ui.api.Printer;
import com.otus.spring.ui.api.commands.Command;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static com.otus.spring.ui.api.AnswersParser.ANSWERS_SEPARATOR;

@Order(1)
@Component
public class WelcomeCommand implements Command {

    private final Printer printer;

    private final MessageSourceHolder messageSourceHolder;

    public WelcomeCommand(Printer printer, MessageSourceHolder messageSourceHolder) {
        this.printer = printer;
        this.messageSourceHolder = messageSourceHolder;
    }

    @Override
    public void run(Object input) {
        String welcome = messageSourceHolder.getMessage("welcome", ANSWERS_SEPARATOR);
        printer.println( welcome );
    }

    @Override
    public Object getResult() {
        return null;
    }

}
