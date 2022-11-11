package com.otus.spring.ui.impl;

import com.otus.spring.ui.api.InputOutputUtils;
import com.otus.spring.ui.api.Printer;
import com.otus.spring.ui.api.Reader;
import org.springframework.stereotype.Component;

@Component
public class InputOutputUtilsImpl implements InputOutputUtils {

    public String readNotBlankInput(Printer printer, Reader reader, String inputMessage) {
        String input;
        do {
            printer.print(inputMessage + " ");
            input = reader.read();
        } while (input.isBlank());
        return input;
    }

}
