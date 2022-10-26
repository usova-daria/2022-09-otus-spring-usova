package com.otus.spring.ui.impl;

import com.otus.spring.ui.api.ApplicationRunner;
import com.otus.spring.ui.api.MessageSourceHolder;
import com.otus.spring.ui.api.Printer;
import com.otus.spring.ui.api.commands.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationRunnerImpl implements ApplicationRunner {

    private final Printer printer;

    private final List<Command> commands;

    private final MessageSourceHolder messageSourceHolder;

    private final Logger logger = LoggerFactory.getLogger(ApplicationRunnerImpl.class);

    public ApplicationRunnerImpl(Printer printer, List<Command> commands, MessageSourceHolder messageSourceHolder) {
        this.printer = printer;
        this.commands = commands;
        this.messageSourceHolder = messageSourceHolder;
    }

    @Override
    public void run() {
        try {
            Object prevResult = null;
            for (Command command : commands) {
                command.run(prevResult);
                prevResult = command.getResult();
            }
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            printer.println(messageSourceHolder.getMessage("unexpected.error"));
        }
    }

}
