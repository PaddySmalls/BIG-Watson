package com.big.watson.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.big.watson.advice.InterceptorType;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.factory.annotation.Autowired;

import com.big.watson.exception.NoSuchInterceptorTypeException;

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

	public List<Advice> getAllAdvicesForBean(String beanClassName) {
		List<Advice> advices = new ArrayList<>();
		try {
			Advised advisedBean = advisedBeanService.getAdvisedBean(Class.forName(beanClassName));
			Arrays.asList(advisedBean.getAdvisors()).forEach(advisor -> {
				Advice advice = advisor.getAdvice();
				if (!advice.getClass().getName().startsWith("org.springframework")) {
					advices.add(advice);
				}
			});
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return advices;
	}

	public boolean removeAdvisorFromBean(String beanClassName, int index) {
		boolean success = false;
		try {
			Advised advisedBean = advisedBeanService.getAdvisedBean(Class.forName(beanClassName));
			List<Advisor> filteredAdvisors = filterSpringAdvisors(Arrays.asList(advisedBean.getAdvisors()));
			for (int i = 0; i < filteredAdvisors.size(); i++) {
				if (i == index) {
					success = advisedBean.removeAdvisor(filteredAdvisors.get(index));
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return success;
	}

	private List<Advisor> filterSpringAdvisors(List<Advisor> advisors) {
		List<Advisor> filteredAdvisors = new ArrayList<>();
		advisors.forEach(advisor -> {
			if (!advisor.getAdvice().getClass().getName().startsWith("org.springframework")) {
				filteredAdvisors.add(advisor);
			}
		});
		return filteredAdvisors;
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
