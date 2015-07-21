package com.big.watson.demo;

import com.big.watson.config.SpringInstrumentationConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by patrick.kleindienst on 20.07.2015.
 */
public class PrototypeTest {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringInstrumentationConfig.class);
		Student student = applicationContext.getBean(Student.class);
		System.out.println(student);
		student = applicationContext.getBean(Student.class);
		System.out.println(student);
	}
}
