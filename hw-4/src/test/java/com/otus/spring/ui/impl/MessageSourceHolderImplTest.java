package com.otus.spring.ui.impl;

import com.otus.spring.config.ContentConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ResourceBundleMessageSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageSourceHolderImplTest {

    final String DEFAULT_MESSAGE = "TBD";

    MessageSourceHolderImpl messageSourceHolder;

    @BeforeEach
    void init() {
        var messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");

        var contentConfig = new ContentConfiguration();
        contentConfig.setCountry("US");
        contentConfig.setLanguage("en");
        contentConfig.setDefaultMessage(DEFAULT_MESSAGE);

        messageSourceHolder = new MessageSourceHolderImpl(messageSource, contentConfig);
    }

    @Test
    void testMessageWithoutParams() {
        var message = messageSourceHolder.getMessage("message.no.params");
        assertEquals("message", message);
    }

    @Test
    void testMessageWithParams() {
        var message = messageSourceHolder.getMessage("message.with.param", "param");
        assertEquals("message param", message);
    }

    @Test
    void testNoSuchMessage() {
        var message = messageSourceHolder.getMessage("message.does.not.exist");
        assertEquals(DEFAULT_MESSAGE, message);
    }

}