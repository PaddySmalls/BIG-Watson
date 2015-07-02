package com.big.instrumentation.spring.service;

import java.lang.reflect.Method;

import com.big.instrumentation.spring.advice.InterceptorType;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;

import com.big.instrumentation.spring.exception.NoSuchInterceptorException;
import com.big.instrumentation.spring.interceptor.PerformanceMeasurementInterceptor;

/**
 * Created by patrick.kleindienst on 18.06.2015.
 */
public class SpringAdvisorBuilder {

	public static NameMatchMethodPointcutAdvisor buildPointcutAdvisor(Method method, InterceptorType interceptorType) {
		NameMatchMethodPointcutAdvisor advisor = new NameMatchMethodPointcutAdvisor();
		advisor.setMappedName(method.getName());
		advisor.setAdvice(enumTypeToInterceptor(interceptorType));
		return advisor;
	}

	private static MethodInterceptor enumTypeToInterceptor(InterceptorType interceptorType) {
		if (interceptorType.equals(InterceptorType.PERFORMANCE)) {
			return new PerformanceMeasurementInterceptor();
		}
		throw new NoSuchInterceptorException();
	}
}
