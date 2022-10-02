package com.otus.spring.ui.impl;

import com.otus.spring.ui.api.ResourceBundleHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.ResourceBundle;

@Component
public class ResourceBundleHolderImpl implements ResourceBundleHolder {

    private final String resourceBundleName;
    private Locale locale;

    public ResourceBundleHolderImpl(@Value("${resourceBundleName}") String resourceBundleName,
                                    @Value("${language}") String language,
                                    @Value("${country}") String country) {
        this.resourceBundleName = resourceBundleName;
        locale = new Locale(language, country);
    }

    @Override
    public ResourceBundle getBundle() {
        return ResourceBundle.getBundle(resourceBundleName, locale);
    }

    @Override
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

}
