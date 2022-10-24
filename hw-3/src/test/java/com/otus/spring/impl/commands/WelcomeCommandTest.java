package com.otus.spring.impl.commands;

import com.otus.spring.config.ContentConfig;
import com.otus.spring.impl.TestPrinter;
import com.otus.spring.ui.api.ResourceBundleHolder;
import com.otus.spring.ui.impl.ConfigurationConstants;
import com.otus.spring.ui.impl.ResourceBundleHolderImpl;
import com.otus.spring.ui.impl.commands.WelcomeCommand;
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
        var contentConfig = new ContentConfig();
        contentConfig.setResourceBundleName("messages");
        contentConfig.setLanguage("ru");
        contentConfig.setCountry("RU");

        printer = new TestPrinter();
        resourceBundleHolder = new ResourceBundleHolderImpl(contentConfig);
        command = new WelcomeCommand(printer, resourceBundleHolder);
    }

    @Test
    void testRun() {
        command.run(null);
        assertEquals("welcome-value-" + ConfigurationConstants.ANSWERS_SEPARATOR + System.lineSeparator(), printer.getPrinted());
        assertNull(command.getResult());
    }

}