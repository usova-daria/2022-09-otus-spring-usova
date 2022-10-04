package com.otus.spring.ui.impl;

import com.otus.spring.ui.api.Printer;
import com.otus.spring.ui.api.Reader;

public class InputOutputUtils {

    private InputOutputUtils() {
        // no instances of this class must be created
    }

    public static String readNotBlankInput(Printer printer, Reader reader, String inputMessage) {
        String input;
        do {
            printer.print(inputMessage + " ");
            input = reader.read();
        } while (input.isBlank());
        return input;
    }

}
