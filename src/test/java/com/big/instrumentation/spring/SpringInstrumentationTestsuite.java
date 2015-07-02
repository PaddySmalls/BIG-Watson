package com.big.instrumentation.spring;

import com.big.instrumentation.spring.context.SpringContextProviderTest;
import com.big.instrumentation.spring.service.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by patrick.kleindienst on 18.06.2015.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({ SpringContextProviderTest.class, SpringAdvisedBeanServiceTest.class, SpringAdvisorBuilderTest.class, AopProxyInstrumentationServiceTest.class })
public class SpringInstrumentationTestsuite {
}
