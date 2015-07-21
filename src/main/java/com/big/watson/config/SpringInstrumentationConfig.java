package com.big.watson.config;

import aj.org.objectweb.asm.ClassVisitor;
import com.big.watson.context.SpringContextProvider;
import com.big.watson.demo.Student;
import com.big.watson.jmx.SpringInstrumentationControllerMBean;
import com.big.watson.scan.MethodInterceptorClassVisitor;
import com.big.watson.scan.MethodInterceptorClasspathScanner;
import com.big.watson.service.AopProxyInstrumentationService;
import org.aopalliance.intercept.MethodInterceptor;
import org.objectweb.asm.Opcodes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.big.watson.service.SpringAdvisedBeanService;
import org.springframework.context.annotation.Scope;
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

}
