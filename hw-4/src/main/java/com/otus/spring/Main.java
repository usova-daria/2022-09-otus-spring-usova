package com.otus.spring;

import com.otus.spring.config.ContentConfig;
import com.otus.spring.ui.api.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.otus.spring.dao",
		"com.otus.spring.service",
		"com.otus.spring.ui"})
@EnableConfigurationProperties(ContentConfig.class)
public class Main {

	public static void main(String[] args) {
		var context = SpringApplication.run(Main.class, args);
		ApplicationRunner applicationRunner = context.getBean(ApplicationRunner.class);
		applicationRunner.run();
	}

}
