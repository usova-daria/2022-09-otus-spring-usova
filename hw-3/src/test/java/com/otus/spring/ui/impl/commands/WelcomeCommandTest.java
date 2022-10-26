package com.otus.spring.ui.impl.commands;

import com.otus.spring.ui.api.MessageSourceHolder;
import com.otus.spring.ui.api.Printer;
import com.otus.spring.ui.impl.ConfigurationConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNull;
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
    void testRun() {
        command.run(null);
        verify(messageSourceHolder).getMessage("welcome", ConfigurationConstants.ANSWERS_SEPARATOR);
        assertNull(command.getResult());
    }

}