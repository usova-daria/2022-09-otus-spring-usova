package com.otus.spring.ui.impl;

import com.otus.spring.config.ContentConfig;
import com.otus.spring.ui.api.MessageSourceHolder;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Locale;

@Component
public class MessageSourceHolderImpl implements MessageSourceHolder {

    private final Locale locale;

    private final MessageSource messageSource;

    private final String defaultMessage;

    public MessageSourceHolderImpl(MessageSource messageSource,
                                   ContentConfig contentConfig) {
        this.messageSource = messageSource;
        this.locale = new Locale(contentConfig.getLanguage(), contentConfig.getCountry());
        this.defaultMessage = contentConfig.getDefaultMessage();
    }

    @Override
    public String getMessage(String key, Object... params) {
        var pattern = messageSource.getMessage(key, null, defaultMessage, locale);
        return pattern == null ? defaultMessage : MessageFormat.format(pattern, params);
    }

}
