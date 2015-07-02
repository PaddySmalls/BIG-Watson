package com.big.instrumentation.spring.service;

import com.big.instrumentation.spring.config.SpringTestContextConfiguration;
import com.big.instrumentation.spring.context.SpringContextProvider;
import dummy.DummyBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by patrick.kleindienst on 18.06.2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringTestContextConfiguration.class)
public class SpringAdvisedBeanServiceTest {

	@Autowired
	private SpringAdvisedBeanService	classUnderTest;

	@Test
	public void testContextProviderIsInjected() {
		SpringContextProvider contextProvider = classUnderTest.getSpringContextProvider();
		assertThat(contextProvider, notNullValue());
	}

	@Test
	public void testRetrieveAdvisedBean() {
		Advised advisedBean = classUnderTest.getAdvisedBean(DummyBean.class);
		assertThat(advisedBean, notNullValue());
		assertThat(AopUtils.isAopProxy(advisedBean), equalTo(true));
	}

}
