package com.big.watson.service;

import java.lang.reflect.Method;

import com.big.watson.advice.InterceptorType;
import com.big.watson.interceptor.PerformanceMeasurementInterceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;

import com.big.watson.exception.NoSuchInterceptorException;

/**
 * Created by patrick.kleindienst on 18.06.2015.
 */
public class SpringAdvisorBuilder {

    public static NameMatchMethodPointcutAdvisor buildPointcutAdvisor(Method method, MethodInterceptor interceptor) {
        NameMatchMethodPointcutAdvisor advisor = new NameMatchMethodPointcutAdvisor();
        advisor.setMappedName(method.getName());
        advisor.setAdvice(interceptor);
        return advisor;
    }

    private static MethodInterceptor enumTypeToInterceptor(InterceptorType interceptorType) {
        if (interceptorType.equals(InterceptorType.PERFORMANCE)) {
            return new PerformanceMeasurementInterceptor();
        }
        throw new NoSuchInterceptorException();
    }
}
