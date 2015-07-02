package com.big.instrumentation.spring.jmx;

import com.big.instrumentation.spring.service.AopProxyInstrumentationService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by patrick.kleindienst on 22.06.2015.
 */
public class SpringInstrumentationControllerMBean {

	public static final String				CONTROLLER_MBEAN_NAME	= "spring.SherLog:name=DebugController";

	private AopProxyInstrumentationService	instrumentationService;

	@Autowired
	public SpringInstrumentationControllerMBean(AopProxyInstrumentationService instrumentationService) {
		this.instrumentationService = instrumentationService;
	}

	public void prepareBean(String className, String methodName, int interceptorIndex) {
		instrumentationService.configureBeanInstrumentation(className, methodName, interceptorIndex);
	}
}
