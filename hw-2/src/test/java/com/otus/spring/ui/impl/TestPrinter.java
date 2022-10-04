package com.otus.spring.ui.impl;

import com.otus.spring.ui.api.Printer;

public class TestPrinter implements Printer {

    private final StringBuilder printed = new StringBuilder();

    @Override
    public void print(String output) {
        printed.append(output);
    }

    @Override
    public void printBlankLine() {
        printed.append(System.lineSeparator());
    }

    @Override
    public void println(String output) {
        printed.append(output).append(System.lineSeparator());
    }

    public String getPrinted() {
        return printed.toString();
    }

}
