package com.otus.spring.ui.impl.commands;

import com.otus.spring.service.api.LocaleService;
import com.otus.spring.ui.api.InputOutputUtils;
import com.otus.spring.ui.api.MessageSourceHolder;
import com.otus.spring.ui.api.Printer;
import com.otus.spring.ui.api.Reader;
import com.otus.spring.ui.api.commands.Command;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class ChangeLocaleCommand implements Command {

    private final LocaleService localeService;
    private final Printer printer;
    private final Reader reader;
    private final MessageSourceHolder messageSourceHolder;
    private final InputOutputUtils inputOutputUtils;

    public ChangeLocaleCommand(LocaleService localeService,
                               Printer printer, Reader reader,
                               MessageSourceHolder messageSourceHolder,
                               InputOutputUtils inputOutputUtils) {
        this.localeService = localeService;
        this.printer = printer;
        this.reader = reader;
        this.messageSourceHolder = messageSourceHolder;
        this.inputOutputUtils = inputOutputUtils;
    }

    @Override
    public void run(Object input) {
        var availableLocales = localeService.getAvailableLocales()
                        .stream().map(l -> l.getLanguage() + " " + l.getCountry())
                .collect(Collectors.joining(", "));
        printer.println(messageSourceHolder.getMessage("available.locales", availableLocales));

        var newLocale = inputOutputUtils.readNotBlankInput(printer, reader, messageSourceHolder.getMessage("locale"));
        var languageAndCountry = newLocale.split("\\s");
        if (languageAndCountry.length == 2) {
            Locale locale = new Locale(languageAndCountry[0], languageAndCountry[1]);
            if (localeService.isLocaleAvailable(locale)) {
                localeService.changeLocale(locale);
                printer.println(messageSourceHolder.getMessage("change.locale.success"));
                return;
            }
        }

        printer.println(messageSourceHolder.getMessage("change.locale.failure"));
    }

    @Override
    public Object getResult() {
        return null;
    }

}
