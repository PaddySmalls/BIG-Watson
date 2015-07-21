package com.big.watson.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Before;
import org.junit.Test;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;

import com.big.watson.interceptor.PerformanceMeasurementInterceptor;

/**
 * Created by patrick.kleindienst on 22.06.2015.
 */

public class SpringAdvisorBuilderTest {

	/*
	 * Use dummy here objects for Method and AdviceType because Mockito can't
	 * mock final classes or enums
	 */
	private Method				dummyMethod;
	private MethodInterceptor	dummyInterceptor;

	@Before
	public void setUp() {
		dummyInterceptor = new PerformanceMeasurementInterceptor();
		try {
			dummyMethod = this.getClass().getDeclaredMethod("dummyMethod", new Class[] {});
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testBuildPointcutAdvisor() {
		NameMatchMethodPointcutAdvisor advisor = SpringAdvisorBuilder.buildPointcutAdvisor(dummyMethod, dummyInterceptor);
		assertThat(advisor.getPointcut().getMethodMatcher().matches(dummyMethod, null), equalTo(true));
		assertThat(advisor.getAdvice(), notNullValue());
	}

	@SuppressWarnings("unused")
	private void dummyMethod() {
		// nothing to do here
	}

}
