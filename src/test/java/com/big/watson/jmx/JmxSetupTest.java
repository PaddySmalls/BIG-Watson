package com.big.watson.jmx;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.big.watson.config.SpringTestContextConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by patrick.kleindienst on 22.06.2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringTestContextConfiguration.class })
@ActiveProfiles("jmx")
public class JmxSetupTest implements ApplicationContextAware {

	private ApplicationContext	applicationContext;
	private String				controllerMBeanName	= "spring-SherLog:name=DebugController";

	@Test
	public void testMBeanServerExists() {
		MBeanServer mBeanServer = applicationContext.getBean(MBeanServer.class);
		assertThat(mBeanServer, notNullValue());
	}

	@Test
	public void testControllerMBeanIsRegistered() throws Exception {
		MBeanServer mBeanServer = applicationContext.getBean(MBeanServer.class);
		assertThat(mBeanServer.isRegistered(new ObjectName(controllerMBeanName)), equalTo(true));
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
