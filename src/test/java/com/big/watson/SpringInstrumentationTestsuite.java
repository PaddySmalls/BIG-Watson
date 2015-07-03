package com.big.watson;

import com.big.watson.context.SpringContextProviderTest;
import com.big.watson.service.AopProxyInstrumentationServiceTest;
import com.big.watson.service.SpringAdvisedBeanServiceTest;
import com.big.watson.service.SpringAdvisorBuilderTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by patrick.kleindienst on 18.06.2015.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({ SpringContextProviderTest.class, SpringAdvisedBeanServiceTest.class, SpringAdvisorBuilderTest.class, AopProxyInstrumentationServiceTest.class })
public class SpringInstrumentationTestsuite {
}
