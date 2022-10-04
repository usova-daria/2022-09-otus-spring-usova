package com.otus.spring.ui.impl;

import com.otus.spring.ui.api.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.util.StringUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InputOutputUtilsTest {

    @Mock
    Reader reader;

    TestPrinter printer;

    @BeforeEach
    void init() {
        reader = mock(Reader.class);
        printer = new TestPrinter();
    }

    @Test
    void testReadNotBlankInput() {
        when(reader.read())
                .thenReturn("")
                .thenReturn("    ")
                .thenReturn("output");

        var message = "message";
        InputOutputUtils.readNotBlankInput(printer, reader, message);
        assertEquals(3, StringUtils.countOccurrencesOf(printer.getPrinted(), message));
    }

}