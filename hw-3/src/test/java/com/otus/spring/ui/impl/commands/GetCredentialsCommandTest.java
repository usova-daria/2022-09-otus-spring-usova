package com.otus.spring.ui.impl.commands;

import com.otus.spring.ui.api.MessageSourceHolder;
import com.otus.spring.ui.api.Printer;
import com.otus.spring.ui.api.Reader;
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

    GetCredentialsCommand command;

    @BeforeEach
    void init() {
        command = new GetCredentialsCommand(printer, reader, messageSourceHolder);
    }

    @Test
    void testRun() {
        var firstName = "first-name";
        var lastName = "last-name";
        when(reader.read())
                .thenReturn(firstName)
                .thenReturn(lastName);

        command.run(null);

        verify(messageSourceHolder).getMessage("first.name");
        verify(messageSourceHolder).getMessage("last.name");

        var credentials = command.getResult();
        assertEquals(firstName, credentials.getFirstName());
        assertEquals(lastName, credentials.getLastName());
    }

    @Test
    void testGetResult_CommandIsNotExecuted() {
        assertThrows(IllegalStateException.class, () -> command.getResult());
    }

}