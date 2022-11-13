package com.otus.spring.ui.impl.commands;

import com.otus.spring.service.api.LocaleService;
import com.otus.spring.ui.api.InputOutputUtils;
import com.otus.spring.ui.api.MessageSourceHolder;
import com.otus.spring.ui.api.Printer;
import com.otus.spring.ui.api.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChangeLocaleCommandTest {

    @Mock
    LocaleService localeService;

    @Mock
    Printer printer;

    @Mock
    Reader reader;

    @Mock
    MessageSourceHolder messageSourceHolder;

    InputOutputUtils inputOutputUtils;

    ChangeLocaleCommand command;

    @BeforeEach
    void init() {
        inputOutputUtils = (printer, reader, inputMessage) -> reader.read();
        command = new ChangeLocaleCommand(localeService, printer, reader, messageSourceHolder, inputOutputUtils);
    }

    @Test
    void availableLocaleWasChosen_Test() {
        when(localeService.getAvailableLocales()).thenReturn(List.of(Locale.FRANCE, Locale.GERMANY));
        when(localeService.isLocaleAvailable(Locale.FRANCE)).thenReturn(true);
        when(reader.read()).thenReturn("fr FR");

        command.run(null);

        verify(messageSourceHolder).getMessage("available.locales", "fr FR, de DE");
        verify(messageSourceHolder).getMessage("locale");
        verify(localeService).changeLocale(Locale.FRANCE);
        verify(messageSourceHolder).getMessage("change.locale.success");
        assertNull(command.getResult());
    }

    @Test
    void notAvailableLocaleWasChosen_Test() {
        when(localeService.getAvailableLocales()).thenReturn(List.of(Locale.FRANCE, Locale.GERMANY));
        when(localeService.isLocaleAvailable(Locale.CANADA)).thenReturn(false);
        when(reader.read()).thenReturn("en CA");

        command.run(null);

        verify(localeService, never()).changeLocale(Locale.CANADA);
        verify(messageSourceHolder).getMessage("change.locale.failure");
    }

    @Test
    void incorrectLocaleInput_Test() {
        when(localeService.getAvailableLocales()).thenReturn(List.of(Locale.FRANCE, Locale.GERMANY));
        when(reader.read()).thenReturn("not a locale");

        command.run(null);

        verify(localeService, never()).changeLocale(any());
        verify(messageSourceHolder).getMessage("change.locale.failure");
    }


}