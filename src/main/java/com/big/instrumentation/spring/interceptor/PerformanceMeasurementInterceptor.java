package com.big.instrumentation.spring.interceptor;

import com.big.instrumentation.spring.logging.LoggerProvider;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Created by patrick.kleindienst on 18.06.2015.
 */
public class PerformanceMeasurementInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		long start = System.currentTimeMillis();
		LoggerProvider.logger.debug("Execution of method " + invocation.getMethod().getName() + " started at: " + start);
		Object result = invocation.proceed();
		long end = System.currentTimeMillis();
		LoggerProvider.logger.debug("Execution took " + (end - start) + " ms");
		return "Test";
	}
}
