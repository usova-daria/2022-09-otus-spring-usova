package com.otus.spring.service.impl;

import com.otus.spring.config.ContentConfiguration;
import com.otus.spring.service.api.LocaleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class LocaleServiceImpl implements LocaleService {

    private final ContentConfiguration contentConfiguration;
    private final List<Locale> availableLocales;

    public LocaleServiceImpl(@Value("${questions.file}") String questionsSourceBase,
                             @Value("${spring.messages.basename}") String messagesBasename,
                             ResourcePatternResolver resourceResolver,
                             ContentConfiguration contentConfiguration) throws IOException {
        this.contentConfiguration = contentConfiguration;
        this.availableLocales = new ArrayList<>();

        var availableMessagesLocales = getLocalesForFile(messagesBasename, "properties", resourceResolver);
        var availableQuestionsLocales = getLocalesForFile(questionsSourceBase, "csv", resourceResolver);
        for (Locale locale : availableMessagesLocales) {
            findLocaleIn(availableQuestionsLocales, locale).ifPresent(availableLocales::add);
        }
    }

    @Override
    public void changeLocale(Locale locale) {
        var availableLocale = findLocaleIn(availableLocales, locale)
                .orElseThrow(() -> new IllegalArgumentException("locale " + locale + " is not supported"));
        contentConfiguration.setLanguage(availableLocale.getLanguage());
        contentConfiguration.setCountry(availableLocale.getCountry());
    }

    @Override
    public boolean isLocaleAvailable(Locale locale) {
        return findLocaleIn(availableLocales, locale).isPresent();
    }

    @Override
    public List<Locale> getAvailableLocales() {
        return availableLocales;
    }

    private List<Locale> getLocalesForFile(String fileBasename, String fileExtension,
                                           ResourcePatternResolver resourceResolver) throws IOException {
        var availableLocales = new ArrayList<Locale>();

        var files = resourceResolver.getResources("classpath:" + fileBasename + "_*." + fileExtension);
        var pattern = Pattern.compile("_\\w+_\\w+");
        for (Resource resource : files) {
            var matcher = pattern.matcher(resource.getFilename());
            if (matcher.find()) {
                var languageAndCountry = matcher.group().substring(1).split("_");
                if (languageAndCountry.length == 2) {
                    availableLocales.add( new Locale(languageAndCountry[0], languageAndCountry[1]) );
                }
            }
        }

        return availableLocales;
    }

    private Optional<Locale> findLocaleIn(List<Locale> locales, Locale locale) {
        return locales.stream().filter(l -> localesAreEqual(l, locale)).findAny();
    }

    private boolean localesAreEqual(Locale locale1, Locale locale2) {
        return locale1.getCountry().equals( locale2.getCountry() ) &&
               locale1.getLanguage().equals( locale2.getLanguage() );
    }

}
