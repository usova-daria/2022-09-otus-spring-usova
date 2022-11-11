package com.otus.spring.config;

import org.jline.reader.History;
import org.jline.reader.impl.history.DefaultHistory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShellConfiguration {

    @Bean
    public History history() {
        return new DefaultHistory() {
            @Override
            public void save() { }
        };
    }

}
