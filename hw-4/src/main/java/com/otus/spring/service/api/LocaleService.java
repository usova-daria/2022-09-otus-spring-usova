package com.otus.spring.service.api;

import java.util.List;
import java.util.Locale;

public interface LocaleService {

    void changeLocale(Locale locale);

    boolean isLocaleAvailable(Locale locale);

    List<Locale> getAvailableLocales();

}
