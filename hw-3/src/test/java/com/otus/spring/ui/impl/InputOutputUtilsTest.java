package com.otus.spring.ui.impl;

import com.otus.spring.ui.api.Printer;
import com.otus.spring.ui.api.Reader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InputOutputUtilsTest {

    @Mock
    Reader reader;

    @Mock
    Printer printer;

    @Test
    void testReadNotBlankInput() {
        when(reader.read())
                .thenReturn("")
                .thenReturn("    ")
                .thenReturn("output");

        var message = "message";
        InputOutputUtils.readNotBlankInput(printer, reader, message);
        verify(printer, times(3)).print("message ");
    }

}