package com.big.watson.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Created by patrick.kleindienst on 20.07.2015.
 */

@Configuration
public class PrototypeConfig {

	@Bean
	@Scope(value = "prototype")
	public Student student() {
		return new Student("Kalle");
	}
}
