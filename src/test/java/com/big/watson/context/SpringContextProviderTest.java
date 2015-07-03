package com.big.watson.context;

import com.big.watson.config.SpringTestContextConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Created by patrick.kleindienst on 18.06.2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringTestContextConfiguration.class)
public class SpringContextProviderTest {

	@Autowired
	private SpringContextProvider	classUnderTest;

	@Test
	public void testGetApplicationContext() {
		ApplicationContext appContext = classUnderTest.getApplicationContext();
		assertThat(appContext, notNullValue());
	}

	@Test
	public void testGetBean() {
		SpringContextProvider contextProviderBean = classUnderTest.getBean(SpringContextProvider.class);
		assertThat(contextProviderBean, notNullValue());
	}
}
