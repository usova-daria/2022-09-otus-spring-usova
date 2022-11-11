package com.otus.spring.ui.impl.commands;

import com.otus.spring.ui.api.MessageSourceHolder;
import com.otus.spring.ui.api.Printer;
import com.otus.spring.ui.api.commands.Command;
import org.springframework.stereotype.Component;

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
        if ( !(input instanceof String) ) {
            throw new IllegalArgumentException("expected input is String");
        }

        String username = (String) input;
        String welcome = messageSourceHolder.getMessage("welcome", username);
        printer.println( welcome );
    }

    @Override
    public Object getResult() {
        return null;
    }

}
