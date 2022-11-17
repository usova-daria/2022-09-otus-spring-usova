package com.otus.spring.service.impl;

import com.otus.spring.config.ContentConfiguration;
import com.otus.spring.service.api.LocaleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocaleServiceImplTest {

    private static final String QUESTIONS = "questions";

    private static final String MESSAGES = "messages";

    private static final String CLASSPATH_QUESTIONS_PATTERN = "classpath:" + QUESTIONS + "_*.csv";

    private static final String CLASSPATH_MESSAGES_PATTERN = "classpath:" + MESSAGES + "_*.properties";

    @Mock
    ResourcePatternResolver resourcePatternResolver;

    LocaleService localeService;

    @Mock
    ContentConfiguration contentConfiguration;

    @Test
    void defaultLocaleIsNotAvailable_test() throws IOException {
        prepareMocksFor();
        assertThrows(IllegalStateException.class, () -> new LocaleServiceImpl(QUESTIONS, MESSAGES,
                resourcePatternResolver, contentConfiguration));
    }

    @Test
    void thereAreQuestionFilesAndMessages_test() throws IOException {
        when(contentConfiguration.getCountry()).thenReturn(Locale.FRANCE.getCountry());
        when(contentConfiguration.getLanguage()).thenReturn(Locale.FRANCE.getLanguage());
        when(resourcePatternResolver.getResources(CLASSPATH_QUESTIONS_PATTERN))
                .thenReturn(new Resource[]{createResourceWithFilename("questions_fr_FR.csv"),
                        createResourceWithFilename("questions_nocountry.csv"),
                        createResourceWithFilename("questions_not_a_language.csv"),
                        createResourceWithFilename("questions_de_DE.csv")});

        when(resourcePatternResolver.getResources(CLASSPATH_MESSAGES_PATTERN))
                .thenReturn(new Resource[]{createResourceWithFilename("messages_fr_FR.properties"),
                        createResourceWithFilename("messages_nocountry.properties"),
                        createResourceWithFilename("messages_lang_country_smth.properties"),
                        createResourceWithFilename("messages_de_DE.properties")});

        localeService = new LocaleServiceImpl(QUESTIONS, MESSAGES, resourcePatternResolver, contentConfiguration);

        assertEquals(List.of(Locale.FRANCE, Locale.GERMANY), localeService.getAvailableLocales());
    }

    @Test
    void thereAreQuestionFilesButMessageFileIsMissing_test() throws IOException {
        prepareMocksFor(List.of(Locale.FRANCE, Locale.GERMANY), List.of(Locale.FRANCE));
        localeService = new LocaleServiceImpl(QUESTIONS, MESSAGES, resourcePatternResolver, contentConfiguration);
        assertEquals(List.of(Locale.FRANCE), localeService.getAvailableLocales());
    }

    @Test
    void thereAreMessagesFilesButQuestionFileIsMissing_test() throws IOException {
        prepareMocksFor(List.of(Locale.FRANCE), List.of(Locale.FRANCE, Locale.GERMANY));
        localeService = new LocaleServiceImpl(QUESTIONS, MESSAGES, resourcePatternResolver, contentConfiguration);
        assertEquals(List.of(Locale.FRANCE), localeService.getAvailableLocales());
    }

    @Test
    void localeIsAvailable_test() throws IOException {
        prepareMocksFor(Locale.FRANCE);
        localeService = new LocaleServiceImpl(QUESTIONS, MESSAGES, resourcePatternResolver, contentConfiguration);
        assertTrue(localeService.isLocaleAvailable(Locale.FRANCE));
    }

    @Test
    void localeIsNotAvailable_test() throws IOException {
        prepareMocksFor(Locale.FRANCE);
        localeService = new LocaleServiceImpl(QUESTIONS, MESSAGES, resourcePatternResolver, contentConfiguration);
        assertFalse(localeService.isLocaleAvailable(Locale.GERMANY));
    }

    @Test
    void changeLocaleToAvailableLocale_test() throws IOException {
        prepareMocksFor(Locale.FRANCE, Locale.GERMANY);

        localeService = new LocaleServiceImpl(QUESTIONS, MESSAGES, resourcePatternResolver, contentConfiguration);
        localeService.changeLocale(Locale.GERMANY);

        verify(contentConfiguration).setCountry(Locale.GERMANY.getCountry());
        verify(contentConfiguration).setLanguage(Locale.GERMANY.getLanguage());
    }

    @Test
    void changeLocaleToNotAvailableLocale_test() throws IOException {
        prepareMocksFor(Locale.FRANCE);

        localeService = new LocaleServiceImpl(QUESTIONS, MESSAGES, resourcePatternResolver, contentConfiguration);

        assertThrows(IllegalArgumentException.class, () -> localeService.changeLocale(Locale.GERMANY));
        verify(contentConfiguration, never()).setCountry(Locale.GERMANY.getCountry());
        verify(contentConfiguration, never()).setLanguage(Locale.GERMANY.getLanguage());
    }

    private void prepareMocksFor(List<Locale> questionLocales, List<Locale> messageLocales) throws IOException {
        var defaultLocale = questionLocales.stream()
                .filter(messageLocales::contains)
                .findFirst()
                .orElse(Locale.FRANCE);
        when(contentConfiguration.getCountry()).thenReturn(defaultLocale.getCountry());
        when(contentConfiguration.getLanguage()).thenReturn(defaultLocale.getLanguage());

        var questionResources = questionLocales.stream()
                .map(l -> QUESTIONS + "_" + l.getLanguage() + "_" + l.getCountry() + ".csv")
                .map(this::createResourceWithFilename)
                .toArray(Resource[]::new);
        when(resourcePatternResolver.getResources(CLASSPATH_QUESTIONS_PATTERN)).thenReturn(questionResources);

        var messagesResources = messageLocales.stream()
                .map(l -> MESSAGES + "_" + l.getLanguage() + "_" + l.getCountry() + ".properties")
                .map(this::createResourceWithFilename)
                .toArray(Resource[]::new);
        when(resourcePatternResolver.getResources(CLASSPATH_MESSAGES_PATTERN)).thenReturn(messagesResources);
    }

    private void prepareMocksFor(Locale... allowedLocales) throws IOException {
        prepareMocksFor(List.of(allowedLocales), List.of(allowedLocales));
    }

    private Resource createResourceWithFilename(String filename) {
        return new Resource() {
            @Override
            public boolean exists() {
                return false;
            }

            @Override
            public URL getURL() {
                return null;
            }

            @Override
            public URI getURI() {
                return null;
            }

            @Override
            public File getFile() {
                return null;
            }

            @Override
            public long contentLength() {
                return 0;
            }

            @Override
            public long lastModified() {
                return 0;
            }

            @Override
            public Resource createRelative(String relativePath) {
                return null;
            }

            @Override
            public String getFilename() {
                return filename;
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public InputStream getInputStream() {
                return null;
            }
        };
    }

}