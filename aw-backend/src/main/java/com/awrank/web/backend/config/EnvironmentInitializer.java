package com.awrank.web.backend.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class EnvironmentInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	public void initialize(ConfigurableApplicationContext ctx) {
		ConfigurableEnvironment environment = ctx.getEnvironment();
		environment.setActiveProfiles("localhost");

	}

}
