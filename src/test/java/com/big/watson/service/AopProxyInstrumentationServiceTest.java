package com.big.watson.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.big.watson.config.SpringTestContextConfiguration;
import dummy.DummyBean;

/**
 * Created by patrick.kleindienst on 22.06.2015.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(WatsonAdvisorBuilder.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringTestContextConfiguration.class })
public class AopProxyInstrumentationServiceTest {

	@Autowired
	private AopProxyInstrumentationService	classUnderTest;

	// ###########################################################
	// # DEPENDENCIES #
	// ###########################################################

	@Autowired
	private DummyBean						dummyBean;

	private SpringAdvisedBeanService		advisedBeanServiceMock;

	private NameMatchMethodPointcutAdvisor	advisorMock;

	private MethodInterceptor				interceptorMock;

	// ###########################################################
	// # BEFORE #
	// ###########################################################

	@Before
	public void setUp() {
		advisedBeanServiceMock = mock(SpringAdvisedBeanService.class);
		when(advisedBeanServiceMock.getAdvisedBean(DummyBean.class)).thenReturn((Advised) dummyBean);
		classUnderTest.setAdvisedBeanService(advisedBeanServiceMock);

		advisorMock = mock(NameMatchMethodPointcutAdvisor.class);
		PowerMockito.mockStatic(WatsonAdvisorBuilder.class);

		interceptorMock = mock(MethodInterceptor.class);
		advisorMock.setAdvice(interceptorMock);

		BDDMockito.given(WatsonAdvisorBuilder.buildPointcutAdvisor(getMethodFromDummyClass("test"), interceptorMock)).willReturn(advisorMock);
	}

	// ###########################################################
	// # UNIT TESTS #
	// ###########################################################

	@Test
	public void testConfigureBeanInstrumentation() {
		classUnderTest.configureBeanInstrumentation(DummyBean.class.getName(), "test", interceptorMock);
		assertThat(isAdvisorApplied((Advised) dummyBean, advisorMock), equalTo(true));
	}

	// ###########################################################
	// # UTIL METHODS #
	// ###########################################################

	private Method getMethodFromDummyClass(String methodName) {
		try {
			return DummyBean.class.getDeclaredMethod(methodName);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	private boolean isAdvisorApplied(Advised advised, Advisor advisor) {
		for (Advisor adv : advised.getAdvisors()) {
			if (adv.equals(advisor)) {
				return true;
			}
		}
		return false;
	}
}
