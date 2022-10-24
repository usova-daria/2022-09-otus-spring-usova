package com.otus.spring.ui.impl;

import com.otus.spring.config.ContentConfig;
import com.otus.spring.ui.api.ResourceBundleHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.ResourceBundle;

@Component
public class ResourceBundleHolderImpl implements ResourceBundleHolder {

    private final String resourceBundleName;
    private final Locale locale;

    public ResourceBundleHolderImpl(ContentConfig contentConfig) {
        this.resourceBundleName = contentConfig.getResourceBundleName();
        locale = new Locale(contentConfig.getLanguage(), contentConfig.getCountry());
    }

    @Override
    public ResourceBundle getBundle() {
        return ResourceBundle.getBundle(resourceBundleName, locale);
    }

}
