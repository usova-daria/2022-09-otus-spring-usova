package com.otus.spring.ui.impl;

import com.otus.spring.ui.api.ApplicationRunner;
import com.otus.spring.ui.api.ResourceBundleHolder;
import com.otus.spring.ui.api.commands.Command;
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
        printer = new TestPrinter();
        commands = List.of(createDummyCommand("first command result"),
                           createDummyCommand("second command result"));
        resourceBundleHolder = new ResourceBundleHolderImpl("messages", "ru", "RU");
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