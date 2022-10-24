package com.otus.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "content")
public class ContentConfig {

    private String resourceBundleName;
    private String language;
    private String country;

    public void setResourceBundleName(String resourceBundleName) {
        this.resourceBundleName = resourceBundleName;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getResourceBundleName() {
        return resourceBundleName;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }

}
