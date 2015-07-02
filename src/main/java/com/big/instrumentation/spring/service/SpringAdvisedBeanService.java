package com.big.instrumentation.spring.service;

import org.springframework.aop.framework.Advised;

import com.big.instrumentation.spring.context.SpringContextProvider;
import com.big.instrumentation.spring.exception.NotAdvisedBeanException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by patrick.kleindienst on 18.06.2015.
 */
public class SpringAdvisedBeanService {

	private SpringContextProvider	springContextProvider;

	@Autowired
	public SpringAdvisedBeanService(SpringContextProvider springContextProvider) {
		this.springContextProvider = springContextProvider;
	}

	public <T extends Advised, S> T getAdvisedBean(Class<S> aClass) {
		S bean = springContextProvider.getBean(aClass);
		T advisedBean = null;
		try {
			advisedBean = (T) bean;
		} catch (ClassCastException e) {
			throw new NotAdvisedBeanException();
		}
		return advisedBean;
	}

	public SpringContextProvider getSpringContextProvider() {
		return springContextProvider;
	}

	public void setSpringContextProvider(SpringContextProvider springContextProvider) {
		this.springContextProvider = springContextProvider;
	}
}
