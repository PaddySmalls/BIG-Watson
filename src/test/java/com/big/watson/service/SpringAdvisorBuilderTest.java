package com.big.watson.service;

import com.big.watson.advice.InterceptorType;
import org.junit.Before;
import org.junit.Test;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;

import java.lang.reflect.Method;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 *
 * Created by patrick.kleindienst on 22.06.2015.
 */

public class SpringAdvisorBuilderTest {

	/*
	 * Use dummy here objects for Method and AdviceType because Mockito can't
	 * mock final classes or enums
	 */
	private Method			dummyMethod;
	private InterceptorType	dummyInterceptorType;

	@Before
	public void setUp() {
		dummyInterceptorType = InterceptorType.PERFORMANCE;
		try {
			dummyMethod = this.getClass().getDeclaredMethod("dummyMethod", new Class[] {});
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testBuildPointcutAdvisor() {
		NameMatchMethodPointcutAdvisor advisor = SpringAdvisorBuilder.buildPointcutAdvisor(dummyMethod, dummyInterceptorType);
		assertThat(advisor.getPointcut().getMethodMatcher().matches(dummyMethod, null), equalTo(true));
		assertThat(advisor.getAdvice(), notNullValue());
	}

	@SuppressWarnings("unused")
	private void dummyMethod() {
		// nothing to do here
	}

}
