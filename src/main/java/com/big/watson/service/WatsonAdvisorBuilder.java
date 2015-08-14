package com.big.watson.service;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;

/**
 * Created by patrick.kleindienst on 18.06.2015.
 */
public class WatsonAdvisorBuilder {

	public static NameMatchMethodPointcutAdvisor buildPointcutAdvisor(Method method, MethodInterceptor interceptor) {
		NameMatchMethodPointcutAdvisor advisor = new NameMatchMethodPointcutAdvisor();
		advisor.setMappedName(method.getName());
		advisor.setAdvice(interceptor);
		return advisor;
	}

}
