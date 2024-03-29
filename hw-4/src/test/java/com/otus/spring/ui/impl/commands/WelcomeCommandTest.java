package com.otus.spring.ui.impl.commands;

import com.otus.spring.ui.api.MessageSourceHolder;
import com.otus.spring.ui.api.Printer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WelcomeCommandTest {

    @Mock
    Printer printer;

    @Mock
    MessageSourceHolder messageSourceHolder;

    WelcomeCommand command;

    @BeforeEach
    void init() {
        command = new WelcomeCommand(printer, messageSourceHolder);
    }

    @Test
    void happyCase_Test() {
        command.run("username");
        verify(messageSourceHolder).getMessage("welcome", "username");
        assertNull(command.getResult());
    }

    @Test
    void invalidArguments_Test() {
        assertThrows(IllegalArgumentException.class, () -> command.run(null));
        assertThrows(IllegalArgumentException.class, () -> command.run(new Object()));
    }

}