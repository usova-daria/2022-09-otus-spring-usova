package com.otus.spring.impl;

import com.otus.spring.config.ContentConfig;
import com.otus.spring.ui.api.ApplicationRunner;
import com.otus.spring.ui.api.ResourceBundleHolder;
import com.otus.spring.ui.api.commands.Command;
import com.otus.spring.ui.impl.ApplicationRunnerImpl;
import com.otus.spring.ui.impl.ResourceBundleHolderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ApplicationRunnerTest {

    TestPrinter printer;

    List<Command> commands;

    ResourceBundleHolder resourceBundleHolder;

    ApplicationRunner applicationRunner;

    @BeforeEach
    void init() {
        var contentConfig = new ContentConfig();
        contentConfig.setResourceBundleName("messages");
        contentConfig.setLanguage("ru");
        contentConfig.setCountry("RU");

        printer = new TestPrinter();
        commands = List.of(createDummyCommand("first command result"),
                           createDummyCommand("second command result"));
        resourceBundleHolder = new ResourceBundleHolderImpl(contentConfig);
        applicationRunner = new ApplicationRunnerImpl(printer, commands, resourceBundleHolder);
    }

    private Command createDummyCommand(String result) {
        var command = mock(Command.class);
        when(command.getResult()).thenReturn(result);
        return command;
    }

    @Test
    void happyCase_Test() {
        applicationRunner.run();

        verify(commands.get(0)).run(null);
        verify(commands.get(1)).run("first command result");
    }

    @Test
    void commandThrowsException_Test() {
        doThrow( new RuntimeException() ).when(commands.get(0)).run(null);

        applicationRunner.run();

        assertTrue(printer.getPrinted().contains("unexpected-error"));
    }

}