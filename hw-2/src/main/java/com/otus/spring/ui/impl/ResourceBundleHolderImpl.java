package com.otus.spring.ui.impl;

import com.otus.spring.ui.api.ResourceBundleHolder;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleHolderImpl implements ResourceBundleHolder {

    private final String resourceBundleName;
    private Locale locale;

    public ResourceBundleHolderImpl(String resourceBundleName, String language, String country) {
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
