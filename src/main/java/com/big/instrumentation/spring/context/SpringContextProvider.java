package com.big.instrumentation.spring.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by patrick.kleindienst on 18.06.2015.
 */

public class SpringContextProvider implements ApplicationContextAware {

	private static ApplicationContext	appContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		appContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return appContext;
	}

	public <T> T getBean(Class<T> aClass) {
		return appContext.getBean(aClass);
	}
}
