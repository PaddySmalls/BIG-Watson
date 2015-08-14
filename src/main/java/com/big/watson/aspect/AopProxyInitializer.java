package com.big.watson.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by patrick.kleindienst on 18.06.2015.
 */

@Aspect
public class AopProxyInitializer {

	@Pointcut("execution(public * *(..))")
	public void publicMethod() {
	}

	@Pointcut("@within(com.big.watson.annotation.WatsonManaged)")
	public void watsonManaged() {
	}

	/*
	 * @Around("publicMethod() && watsonManaged()") public Object
	 * showInstrumentationOutput(ProceedingJoinPoint joinPoint) { try { return
	 * joinPoint.proceed(); } catch (Throwable throwable) {
	 * throwable.printStackTrace(); } return null; }
	 */

	@Before("publicMethod() && watsonManaged()")
	public void showOutput() {

	}
}
