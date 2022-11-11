package com.otus.spring.ui.impl;

import com.otus.spring.ui.api.ApplicationRunner;
import com.otus.spring.ui.api.MessageSourceHolder;
import com.otus.spring.ui.api.Printer;
import com.otus.spring.ui.api.commands.Command;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationRunnerTest {

    @Mock
    Printer printer;

    @Mock
    MessageSourceHolder messageSourceHolder;

    List<Command> commands;

    ApplicationRunner applicationRunner;

    @BeforeEach
    void init() {
        commands = List.of(createDummyCommand("first command result"),
                           createDummyCommand("second command result"));
        applicationRunner = new ApplicationRunnerImpl(printer, commands, messageSourceHolder);
    }

    private Command createDummyCommand(String result) {
        var command = mock(Command.class);
        lenient().when(command.getResult()).thenReturn(result);
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
        var command = mock(Command.class);
        doThrow( new RuntimeException() ).when(command).run(null);
        applicationRunner = new ApplicationRunnerImpl(printer, List.of(command), messageSourceHolder);

        applicationRunner.run();

        verify(messageSourceHolder).getMessage("unexpected.error");
    }

}