package com.big.instrumentation.spring.demo;

import com.big.instrumentation.spring.annotation.SherlogManaged;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.big.instrumentation.spring.config.SpringInstrumentationConfig;

/**
 * Created by patrick.kleindienst on 23.06.2015.
 */

@SherlogManaged
@Controller
@EnableAutoConfiguration
@Import(value = { SpringInstrumentationConfig.class })
public class SampleController {

	@RequestMapping("/")
	@ResponseBody
	public String home() {
		return "Hello World!";
	}

	public static void main(String[] args) {
		SpringApplication.run(SampleController.class);
	}
}
