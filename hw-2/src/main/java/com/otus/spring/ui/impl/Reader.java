package com.otus.spring.ui.impl;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Reader {

    private final Scanner SCANNER = new Scanner(System.in);

    public String read() {
        return SCANNER.nextLine();
    }

}
