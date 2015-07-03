package com.big.watson.config;

import com.big.watson.context.SpringContextProvider;
import com.big.watson.jmx.SpringInstrumentationControllerMBean;
import com.big.watson.service.AopProxyInstrumentationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.big.watson.service.SpringAdvisedBeanService;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.jmx.support.MBeanServerFactoryBean;

import javax.management.MBeanServer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by patrick.kleindienst on 18.06.2015.
 */

@Configuration
@ImportResource("classpath:appContext.xml")
public class SpringInstrumentationConfig {

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
	public SpringInstrumentationControllerMBean controllerMBean(AopProxyInstrumentationService instrumentationService) {
		return new SpringInstrumentationControllerMBean(instrumentationService);
	}

	@Bean
	public MBeanServer mBeanServer() {
		MBeanServerFactoryBean serverFactoryBean = new MBeanServerFactoryBean();
		serverFactoryBean.setLocateExistingServerIfPossible(true);
		serverFactoryBean.afterPropertiesSet();
		return serverFactoryBean.getObject();

	}

	@Bean
	public MBeanExporter mBeanExporter(SpringInstrumentationControllerMBean controllerMBean) {
		MBeanExporter mBeanExporter = new MBeanExporter();
		Map<String, Object> beans = new HashMap<>();
		beans.put(SpringInstrumentationControllerMBean.CONTROLLER_MBEAN_NAME, controllerMBean);
		mBeanExporter.setBeans(beans);
		return mBeanExporter;
	}
}
