package com.big.watson.service;

import com.big.watson.context.SpringContextProvider;
import com.big.watson.exception.NotAdvisedBeanException;
import org.springframework.aop.framework.Advised;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by patrick.kleindienst on 18.06.2015.
 */
public class SpringAdvisedBeanService {

	private SpringContextProvider springContextProvider;

	@Autowired public SpringAdvisedBeanService(SpringContextProvider springContextProvider)
	{
		this.springContextProvider = springContextProvider;
	}

	public <T> Advised getAdvisedBean(Class<T> aClass)
	{
		T bean = springContextProvider.getBean(aClass);
		Advised advisedBean = null;
		try
		{
			advisedBean = (Advised) bean;
		}
		catch (ClassCastException e)
		{
			throw new NotAdvisedBeanException();
		}
		return advisedBean;
	}

	public SpringContextProvider getSpringContextProvider()
	{
		return springContextProvider;
	}

	public void setSpringContextProvider(SpringContextProvider springContextProvider) {
		this.springContextProvider = springContextProvider;
	}
}
