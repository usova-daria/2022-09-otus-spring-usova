package com.otus.spring.ui.impl;

import com.otus.spring.config.ContentConfiguration;
import com.otus.spring.ui.api.MessageSourceHolder;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Locale;

@Component
public class MessageSourceHolderImpl implements MessageSourceHolder {

    private final ContentConfiguration contentConfiguration;

    private final MessageSource messageSource;

    private final String defaultMessage;

    public MessageSourceHolderImpl(MessageSource messageSource,
                                   ContentConfiguration contentConfiguration) {
        this.messageSource = messageSource;
        this.contentConfiguration = contentConfiguration;
        this.defaultMessage = contentConfiguration.getDefaultMessage();
    }

    @Override
    public String getMessage(String key, Object... params) {
        var pattern = messageSource.getMessage(key, null, defaultMessage, getLocale());
        return pattern == null ? defaultMessage : MessageFormat.format(pattern, params);
    }

    private Locale getLocale() {
        return new Locale(contentConfiguration.getLanguage(), contentConfiguration.getCountry());
    }

}
