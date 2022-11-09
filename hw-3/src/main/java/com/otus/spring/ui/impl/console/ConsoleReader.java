package com.otus.spring.ui.impl.console;

import com.otus.spring.ui.api.Reader;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleReader implements Reader {

    private final Scanner SCANNER = new Scanner(System.in);

    @Override
    public String read() {
        return SCANNER.nextLine();
    }

}
