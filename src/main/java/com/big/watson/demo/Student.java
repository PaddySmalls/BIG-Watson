package com.big.watson.demo;

import com.big.watson.annotation.WatsonManaged;

/**
 * Created by patrick.kleindienst on 20.07.2015.
 */
@WatsonManaged
public class Student {

	private String	fullName;

	public Student(String fullName) {
		this.fullName = fullName;
	}

	public String getFullName() {
		return fullName;
	}
}
