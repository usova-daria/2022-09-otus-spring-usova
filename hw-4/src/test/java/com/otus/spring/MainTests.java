package com.otus.spring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.shell.interactive.enabled=false")
class MainTests {

	@Test
	void contextLoads() {
	}

}
