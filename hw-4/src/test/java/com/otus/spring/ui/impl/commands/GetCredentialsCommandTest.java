package com.otus.spring.ui.impl.commands;

import com.otus.spring.ui.api.MessageSourceHolder;
import com.otus.spring.ui.api.Printer;
import com.otus.spring.ui.api.Reader;
import com.otus.spring.ui.api.InputOutputUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetCredentialsCommandTest {

    @Mock
    Reader reader;

    @Mock
    Printer printer;

    @Mock
    MessageSourceHolder messageSourceHolder;

    InputOutputUtils inputOutputUtils;

    GetCredentialsCommand command;

    @BeforeEach
    void init() {
        inputOutputUtils = (printer, reader, inputMessage) -> reader.read();
        command = new GetCredentialsCommand(printer, reader, messageSourceHolder, inputOutputUtils);
    }

    @Test
    void testRun() {
        var username = "username";
        when(reader.read())
                .thenReturn(username);

        command.run(null);

        verify(messageSourceHolder).getMessage("username");

        var credentials = command.getResult();
        assertEquals(username, credentials.getUsername());
    }

    @Test
    void testGetResult_CommandIsNotExecuted() {
        assertThrows(IllegalStateException.class, () -> command.getResult());
    }

}