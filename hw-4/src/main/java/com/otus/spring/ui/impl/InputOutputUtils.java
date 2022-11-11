package com.otus.spring.ui.impl;

import com.otus.spring.ui.api.Printer;
import com.otus.spring.ui.api.Reader;

public interface InputOutputUtils {

    String readNotBlankInput(Printer printer, Reader reader, String inputMessage);

}
