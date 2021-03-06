package com.big.watson.config;

import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanServer;

import org.objectweb.asm.Opcodes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.jmx.support.MBeanServerFactoryBean;

import com.big.watson.aspect.AopProxyInitializer;
import com.big.watson.context.SpringContextProvider;
import com.big.watson.demo.Student;
import com.big.watson.jmx.WatsonControllerMBean;
import com.big.watson.scan.MethodInterceptorClassVisitor;
import com.big.watson.scan.MethodInterceptorClasspathScanner;
import com.big.watson.service.AopProxyInstrumentationService;
import com.big.watson.service.SpringAdvisedBeanService;

/**
 * Created by patrick.kleindienst on 18.06.2015.
 */

@Configuration
@EnableAspectJAutoProxy
public class WatsonConfig
{

	@Bean
	public SpringContextProvider springContextProvider() {
		return new SpringContextProvider();
	}

	@Bean
	public SpringAdvisedBeanService springAdvisedBeanService(SpringContextProvider springContextProvider) {
		return new SpringAdvisedBeanService(springContextProvider);
	}

	@Bean
	public WatsonControllerMBean controllerMBean(AopProxyInstrumentationService instrumentationService) {
		return new WatsonControllerMBean(instrumentationService);
	}

	@Bean
	public MBeanServer mBeanServer() {
		MBeanServerFactoryBean serverFactoryBean = new MBeanServerFactoryBean();
		serverFactoryBean.setLocateExistingServerIfPossible(true);
		serverFactoryBean.afterPropertiesSet();
		return serverFactoryBean.getObject();

	}

	@Bean
	public MBeanExporter mBeanExporter(WatsonControllerMBean controllerMBean) {
		MBeanExporter mBeanExporter = new MBeanExporter();
		Map<String, Object> beans = new HashMap<>();
		beans.put(WatsonControllerMBean.CONTROLLER_MBEAN_NAME, controllerMBean);
		mBeanExporter.setBeans(beans);
		return mBeanExporter;
	}

	@Bean
	@Scope(value = "prototype")
	public Student student() {
		return new Student("Kalle");
	}

	@Bean
	public AopProxyInstrumentationService instrumentationService(SpringAdvisedBeanService advisedBeanService, MethodInterceptorClasspathScanner classpathScanner) {
		return new AopProxyInstrumentationService(advisedBeanService, classpathScanner);
	}

	@Bean
	public MethodInterceptorClassVisitor classVisitor() {
		return new MethodInterceptorClassVisitor(Opcodes.ASM5);
	}

	@Bean
	public MethodInterceptorClasspathScanner classpathScanner(MethodInterceptorClassVisitor classVisitor) {
		return new MethodInterceptorClasspathScanner(classVisitor);
	}

	@Bean
	public AopProxyInitializer proxyInitializer() {
		return new AopProxyInitializer();
	}

}
