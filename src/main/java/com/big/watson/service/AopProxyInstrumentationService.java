package com.big.watson.service;

import com.big.watson.scan.MethodInterceptorClasspathScanner;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by patrick.kleindienst on 22.06.2015.
 */

public class AopProxyInstrumentationService {

    private SpringAdvisedBeanService advisedBeanService;
    private MethodInterceptorClasspathScanner classpathScanner;


    @Autowired
    public AopProxyInstrumentationService(SpringAdvisedBeanService advisedBeanService,
                                          MethodInterceptorClasspathScanner classpathScanner) {
        this.advisedBeanService = advisedBeanService;
        this.classpathScanner = classpathScanner;
    }

    public void configureBeanInstrumentation(String beanClassName, String methodName, MethodInterceptor interceptor) {
        try {
            Class<?> beanClass = Class.forName(beanClassName);
            Advised advisedBean = advisedBeanService.getAdvisedBean(beanClass);

            for (Method method : beanClass.getDeclaredMethods()) {
                if (method.getName().equalsIgnoreCase(methodName)) {
                    advisedBean.addAdvisor(SpringAdvisorBuilder.buildPointcutAdvisor(method,
                            interceptor));
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Advice> getAllAdvicesForBean(String beanClassName) {
        List<Advice> advices = new ArrayList<>();
        try {
            Advised advisedBean = advisedBeanService.getAdvisedBean(Class.forName(beanClassName));
            Arrays.asList(advisedBean.getAdvisors()).forEach(advisor -> {
                Advice advice = advisor.getAdvice();
                if (!advice.getClass().getName().startsWith("org.springframework")) {
                    advices.add(advice);
                }
            });
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return advices;
    }

    public boolean removeAdvisorFromBean(String beanClassName, int index) {
        boolean success = false;
        try {
            Advised advisedBean = advisedBeanService.getAdvisedBean(Class.forName(beanClassName));
            List<Advisor> filteredAdvisors = filterSpringAdvisors(Arrays.asList(advisedBean.getAdvisors()));
            for (int i = 0; i < filteredAdvisors.size(); i++) {
                if (i == index) {
                    success = advisedBean.removeAdvisor(filteredAdvisors.get(index));
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return success;
    }


    public Set<MethodInterceptor> getAvailableMethodInterceptors() {
        return classpathScanner.loadMethodInterceptors();
    }

    private List<Advisor> filterSpringAdvisors(List<Advisor> advisors) {
        List<Advisor> filteredAdvisors = new ArrayList<>();
        advisors.forEach(advisor -> {
            if (!advisor.getAdvice().getClass().getName().startsWith("org.springframework")) {
                filteredAdvisors.add(advisor);
            }
        });
        return filteredAdvisors;

    }

    public SpringAdvisedBeanService getAdvisedBeanService() {
        return advisedBeanService;
    }

    public void setAdvisedBeanService(SpringAdvisedBeanService advisedBeanService) {
        this.advisedBeanService = advisedBeanService;
    }
}
