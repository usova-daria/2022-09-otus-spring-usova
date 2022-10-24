package com.otus.spring.impl.commands;

import com.otus.spring.config.ContentConfig;
import com.otus.spring.impl.TestPrinter;
import com.otus.spring.ui.api.Reader;
import com.otus.spring.ui.api.ResourceBundleHolder;
import com.otus.spring.ui.impl.ResourceBundleHolderImpl;
import com.otus.spring.ui.impl.commands.GetCredentialsCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetCredentialsCommandTest {

    @Mock
    Reader reader;

    TestPrinter printer;

    ResourceBundleHolder resourceBundleHolder;

    GetCredentialsCommand command;

    @BeforeEach
    void init() {
        var contentConfig = new ContentConfig();
        contentConfig.setResourceBundleName("messages");
        contentConfig.setLanguage("ru");
        contentConfig.setCountry("RU");

        printer = new TestPrinter();
        resourceBundleHolder = new ResourceBundleHolderImpl(contentConfig);
        command = new GetCredentialsCommand(printer, reader, resourceBundleHolder);
    }

    @Test
    void testRun() {
        var firstName = "first-name";
        var lastName = "last-name";
        when(reader.read())
                .thenReturn(firstName)
                .thenReturn(lastName);

        command.run(null);
        assertTrue(printer.getPrinted().contains("first-name-value"));
        assertTrue(printer.getPrinted().contains("last-name-value"));

        var credentials = command.getResult();
        assertEquals(firstName, credentials.getFirstName());
        assertEquals(lastName, credentials.getLastName());
    }

    @Test
    void testGetResult_CommandIsNotExecuted() {
        assertThrows(IllegalStateException.class, () -> command.getResult());
    }

}