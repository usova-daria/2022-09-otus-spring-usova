package com.otus.spring.ui.impl.commands;

import com.otus.spring.ui.api.Printer;
import com.otus.spring.ui.api.ResourceBundleHolder;
import com.otus.spring.ui.api.commands.Command;
import com.otus.spring.ui.impl.ConfigurationConstants;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Order(1)
@Component
public class WelcomeCommand implements Command {

    private final Printer printer;

    private final ResourceBundleHolder resourceBundleHolder;

    public WelcomeCommand(Printer printer, ResourceBundleHolder resourceBundleHolder) {
        this.printer = printer;
        this.resourceBundleHolder = resourceBundleHolder;
    }

    @Override
    public void run(Object input) {
        String pattern = resourceBundleHolder.getBundle().getString("welcome");
        printer.println( MessageFormat.format(pattern, ConfigurationConstants.ANSWERS_SEPARATOR) );
    }

    @Override
    public Object getResult() {
        return null;
    }

}
