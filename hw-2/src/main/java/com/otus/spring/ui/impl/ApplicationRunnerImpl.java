package com.otus.spring.ui.impl;

import com.otus.spring.ui.api.ApplicationRunner;
import com.otus.spring.ui.api.Printer;
import com.otus.spring.ui.api.ResourceBundleHolder;
import com.otus.spring.ui.api.commands.Command;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationRunnerImpl implements ApplicationRunner {

    private final Printer printer;

    private final List<Command> commands;

    private final ResourceBundleHolder resourceBundleHolder;

    public ApplicationRunnerImpl(Printer printer, List<Command> commands, ResourceBundleHolder resourceBundleHolder) {
        this.printer = printer;
        this.commands = commands;
        this.resourceBundleHolder = resourceBundleHolder;
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
            e.printStackTrace();
            printer.println(resourceBundleHolder.getBundle().getString("unexpected.error"));
        }
    }

}
