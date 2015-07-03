package com.big.watson.interceptor;

import com.big.watson.logging.LoggerProvider;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.time.LocalDateTime;

/**
 * Created by patrick.kleindienst on 18.06.2015.
 */
public class PerformanceMeasurementInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		long start = System.currentTimeMillis();
		LoggerProvider.logger.debug("Execution of method " + invocation.getMethod().getName() + " started at: " + LocalDateTime.now());
		Object result = invocation.proceed();
		long end = System.currentTimeMillis();
		LoggerProvider.logger.debug("Execution of method " + invocation.getMethod().getName() + " stopped at: " + LocalDateTime.now());
		LoggerProvider.logger.debug("Execution took " + (end - start) + " ms");
		return result;
	}
}
