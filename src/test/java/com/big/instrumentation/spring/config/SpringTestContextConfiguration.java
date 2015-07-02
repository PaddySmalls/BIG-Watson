package com.big.instrumentation.spring.config;

import com.big.instrumentation.spring.jmx.SpringInstrumentationControllerMBean;
import dummy.DummyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.big.instrumentation.spring.context.SpringContextProvider;
import com.big.instrumentation.spring.service.AopProxyInstrumentationService;
import com.big.instrumentation.spring.service.SpringAdvisedBeanService;
import org.springframework.context.annotation.Profile;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.jmx.support.MBeanServerFactoryBean;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by patrick.kleindienst on 18.06.2015.
 */

@Configuration
@ImportResource("classpath:testAppContext.xml")
public class SpringTestContextConfiguration {

	@Bean
	public SpringContextProvider springContextProvider() {
		return new SpringContextProvider();
	}

	@Bean
	public SpringAdvisedBeanService springAdvisedBeanService(SpringContextProvider springContextProvider) {
		return new SpringAdvisedBeanService(springContextProvider);
	}

	@Bean
	public AopProxyInstrumentationService instrumentationService(SpringAdvisedBeanService advisedBeanService) {
		return new AopProxyInstrumentationService(advisedBeanService);
	}

	@Bean
	public DummyBean dummyBean() {
		return new DummyBean();
	}

	@Bean
	public SpringInstrumentationControllerMBean controllerMBean(AopProxyInstrumentationService instrumentationService) {
		return new SpringInstrumentationControllerMBean(instrumentationService);
	}

	@Bean
	@Profile("jmx")
	public MBeanServer mBeanServer() {
		MBeanServerFactoryBean serverFactoryBean = new MBeanServerFactoryBean();
		serverFactoryBean.setLocateExistingServerIfPossible(true);
		serverFactoryBean.afterPropertiesSet();
		return serverFactoryBean.getObject();

	}

	@Bean
	@Profile("jmx")
	public MBeanExporter mBeanExporter(SpringInstrumentationControllerMBean controllerMBean) {
		MBeanExporter mBeanExporter = new MBeanExporter();
		Map<String, Object> beans = new HashMap<>();
		beans.put(SpringInstrumentationControllerMBean.CONTROLLER_MBEAN_NAME, controllerMBean);
		mBeanExporter.setBeans(beans);
		return mBeanExporter;
	}

}
