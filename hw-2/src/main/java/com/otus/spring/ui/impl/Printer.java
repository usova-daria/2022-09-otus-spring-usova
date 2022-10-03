package com.otus.spring.ui.impl;

import org.springframework.stereotype.Component;

@Component
public class Printer {

    public final static String NEW_LINE = System.lineSeparator();

    public void print(String output) {
        System.out.print(output);
    }

    public void printBlankLine() {
        print(NEW_LINE);
    }

    public void println(String output) {
        print(output + NEW_LINE);
    }

}
