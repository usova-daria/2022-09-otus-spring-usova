package com.otus.spring.ui.impl.commands;

import com.otus.spring.ui.api.ResourceBundleHolder;
import com.otus.spring.ui.impl.ConfigurationConstants;
import com.otus.spring.ui.impl.ResourceBundleHolderImpl;
import com.otus.spring.ui.impl.TestPrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class WelcomeCommandTest {

    TestPrinter printer;

    ResourceBundleHolder resourceBundleHolder;

    WelcomeCommand command;

    @BeforeEach
    void init() {
        printer = new TestPrinter();
        resourceBundleHolder = new ResourceBundleHolderImpl("messages", "ru", "RU");
        command = new WelcomeCommand(printer, resourceBundleHolder);
    }

    @Test
    void testRun() {
        command.run(null);
        assertEquals("welcome-value-" + ConfigurationConstants.ANSWERS_SEPARATOR + System.lineSeparator(), printer.getPrinted());
        assertNull(command.getResult());
    }

}