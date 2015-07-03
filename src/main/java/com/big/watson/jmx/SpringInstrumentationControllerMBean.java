package com.big.watson.jmx;

import com.big.watson.service.AopProxyInstrumentationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patrick.kleindienst on 22.06.2015.
 */
public class SpringInstrumentationControllerMBean
{

    public static final String CONTROLLER_MBEAN_NAME = "spring.watson:name=WatsonController";

    private AopProxyInstrumentationService instrumentationService;

    @Autowired public SpringInstrumentationControllerMBean(AopProxyInstrumentationService instrumentationService)
    {
        this.instrumentationService = instrumentationService;
    }

    public void prepareBean(String className, String methodName, int interceptorIndex)
    {
        instrumentationService.configureBeanInstrumentation(className, methodName, interceptorIndex);
    }

    public List<String> listInterceptorsForBean(String beanClassName)
    {
        List<String> adviceNames = new ArrayList<>();
        instrumentationService.getAllAdvicesForBean(beanClassName)
                .forEach(advice -> adviceNames.add(advice.getClass().getName() + " [" + adviceNames.size() + "]"));
        return adviceNames;
    }

    public String removeInterceptorsFromBean(String beanClassName, int index)
    {
        if (instrumentationService.removeAdvisorFromBean(beanClassName, index))
        {
            return "Successfully removed interceptor with index [" + index + "]";
        }
        return "Interceptor was not found or could not be removed";
    }
}
