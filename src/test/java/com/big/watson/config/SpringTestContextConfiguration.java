package com.big.watson.config;

import com.big.watson.aspect.AopProxyInitializer;
import com.big.watson.jmx.WatsonControllerMBean;
import dummy.DummyBean;
import org.springframework.context.annotation.*;

import com.big.watson.context.SpringContextProvider;
import com.big.watson.service.AopProxyInstrumentationService;
import com.big.watson.service.SpringAdvisedBeanService;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.jmx.support.MBeanServerFactoryBean;

import javax.management.MBeanServer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by patrick.kleindienst on 18.06.2015.
 */

@Configuration
@EnableAspectJAutoProxy
public class SpringTestContextConfiguration {

    @Bean
    public SpringContextProvider springContextProvider() {
        return new SpringContextProvider();
    }

    @Bean
    public SpringAdvisedBeanService springAdvisedBeanService(SpringContextProvider springContextProvider) {
        return new SpringAdvisedBeanService(springContextProvider);
    }

    @Bean
    public AopProxyInstrumentationService instrumentationService(SpringAdvisedBeanService advisedBeanService) {
        return new AopProxyInstrumentationService(advisedBeanService, null);
    }

    @Bean
    public DummyBean dummyBean() {
        return new DummyBean();
    }

    @Bean
    public WatsonControllerMBean controllerMBean(AopProxyInstrumentationService instrumentationService) {
        return new WatsonControllerMBean(instrumentationService);
    }

    @Bean
    @Profile("jmx")
    public MBeanServer mBeanServer() {
        MBeanServerFactoryBean serverFactoryBean = new MBeanServerFactoryBean();
        serverFactoryBean.setLocateExistingServerIfPossible(true);
        serverFactoryBean.afterPropertiesSet();
        return serverFactoryBean.getObject();

    }

    @Bean
    @Profile("jmx")
    public MBeanExporter mBeanExporter(WatsonControllerMBean controllerMBean) {
        MBeanExporter mBeanExporter = new MBeanExporter();
        Map<String, Object> beans = new HashMap<>();
        beans.put(WatsonControllerMBean.CONTROLLER_MBEAN_NAME, controllerMBean);
        mBeanExporter.setBeans(beans);
        return mBeanExporter;
    }

    @Bean
    public AopProxyInitializer proxyInitializer()
    {
        return new AopProxyInitializer();
    }

}
