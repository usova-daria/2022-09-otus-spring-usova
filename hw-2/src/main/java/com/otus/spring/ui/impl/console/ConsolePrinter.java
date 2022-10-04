package com.otus.spring.ui.impl.console;

import com.otus.spring.ui.api.Printer;
import org.springframework.stereotype.Component;

@Component
public class ConsolePrinter implements Printer {

    public final static String NEW_LINE = System.lineSeparator();

    @Override
    public void print(String output) {
        System.out.print(output);
    }

    @Override
    public void printBlankLine() {
        print(NEW_LINE);
    }

    @Override
    public void println(String output) {
        print(output + NEW_LINE);
    }

}
