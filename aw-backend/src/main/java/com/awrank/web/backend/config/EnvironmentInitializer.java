package com.awrank.web.backend.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class EnvironmentInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	private static final String PROFILE_LOCALHOST = "localhost";

	public void initialize(ConfigurableApplicationContext ctx) {
		ConfigurableEnvironment environment = ctx.getEnvironment();
		environment.setActiveProfiles(PROFILE_LOCALHOST);
	}

}
