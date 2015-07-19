package com.big.watson.jmx;

import com.big.watson.exception.NoSuchInterceptorException;
import com.big.watson.interceptor.PerformanceMeasurementInterceptor;
import com.big.watson.scan.MethodInterceptorClasspathScanner;
import com.big.watson.service.AopProxyInstrumentationService;
import com.big.watson.util.WatsonUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.management.MBeanException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by patrick.kleindienst on 22.06.2015.
 */
public class SpringInstrumentationControllerMBean {

    public static final String CONTROLLER_MBEAN_NAME = "spring.watson:name=WatsonController";


    private AopProxyInstrumentationService instrumentationService;
    private MethodInterceptor currentInterceptor;

    @Autowired
    public SpringInstrumentationControllerMBean(AopProxyInstrumentationService instrumentationService) {
        this.instrumentationService = instrumentationService;
        this.currentInterceptor = new PerformanceMeasurementInterceptor();
    }

    public void prepareBean(String className, String methodName) {
        instrumentationService.configureBeanInstrumentation(className, methodName, currentInterceptor);
    }

    public List<String> listInterceptorsForBean(String beanClassName) {
        List<String> adviceNames = new ArrayList<>();
        instrumentationService.getAllAdvicesForBean(beanClassName)
                .forEach(advice -> adviceNames.add(advice.getClass().getName() + " [" + adviceNames.size() + "]"));
        return adviceNames;
    }

    public String removeInterceptorsFromBean(String beanClassName, int index) {
        if (instrumentationService.removeAdvisorFromBean(beanClassName, index)) {
            return "Successfully removed interceptor with index [" + index + "]";
        }
        return "Interceptor was not found or could not be removed";
    }

    public List<String> showAvailableInterceptors() {
        List<String> interceptorNames = new ArrayList<>();
        int interceptorIndex = 0;
        for (MethodInterceptor methodInterceptor : instrumentationService.getAvailableMethodInterceptors()) {
            interceptorNames.add(methodInterceptor.getClass().getSimpleName() + " [" + interceptorIndex++ + "]");
        }
        return interceptorNames;
    }

    public String selectCurrentInterceptor(int index) throws MBeanException {
        if (index >= 0 && index <= instrumentationService.getAvailableMethodInterceptors().size()) {
            currentInterceptor = WatsonUtils.setToList(instrumentationService.getAvailableMethodInterceptors()).get
                    (index);
            return "Current interceptor is: " + currentInterceptor.getClass().getSimpleName();
        }
        return "Index did not match a registered MethodInterceptor!";

    }
}
