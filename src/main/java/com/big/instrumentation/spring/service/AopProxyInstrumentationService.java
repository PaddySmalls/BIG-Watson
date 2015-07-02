package com.big.instrumentation.spring.service;

import java.lang.reflect.Method;

import org.springframework.aop.framework.Advised;
import org.springframework.beans.factory.annotation.Autowired;

import com.big.instrumentation.spring.advice.InterceptorType;
import com.big.instrumentation.spring.exception.NoSuchInterceptorTypeException;

/**
 * Created by patrick.kleindienst on 22.06.2015.
 */

public class AopProxyInstrumentationService {

	private SpringAdvisedBeanService	advisedBeanService;

	@Autowired
	public AopProxyInstrumentationService(SpringAdvisedBeanService advisedBeanService) {
		this.advisedBeanService = advisedBeanService;
	}

	public void configureBeanInstrumentation(String beanClassName, String methodName, int interceptorTypeIndex) {
		try {
			Class<?> beanClass = Class.forName(beanClassName);
			Advised advisedBean = advisedBeanService.getAdvisedBean(beanClass);

			for (Method method : beanClass.getDeclaredMethods()) {
				if (method.getName().equalsIgnoreCase(methodName)) {
					advisedBean.addAdvisor(SpringAdvisorBuilder.buildPointcutAdvisor(method, getInterceptorTypeFromIndex(interceptorTypeIndex)));
				}
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private InterceptorType getInterceptorTypeFromIndex(int interceptorIndex) {
		for (InterceptorType interceptorType : InterceptorType.values()) {
			if (interceptorType.ordinal() == interceptorIndex) {
				return interceptorType;
			}
		}
		throw new NoSuchInterceptorTypeException();
	}

	public SpringAdvisedBeanService getAdvisedBeanService() {
		return advisedBeanService;
	}

	public void setAdvisedBeanService(SpringAdvisedBeanService advisedBeanService) {
		this.advisedBeanService = advisedBeanService;
	}
}
