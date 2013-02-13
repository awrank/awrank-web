package com.awrank.web.backend.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;


//@PropertySource("/WEB-INF/properties/application.properties")
public class EnvironmentInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	private static final String PROFILE_LOCALHOST = "localhost";
	private static final String PROFILE_PRODUCTION = "production";

	public void initialize(ConfigurableApplicationContext ctx) {
		ConfigurableEnvironment environment = ctx.getEnvironment();

		Boolean isProduction = Boolean.parseBoolean(environment.getProperty("build.production.profile"));
		if (isProduction) {
			environment.setActiveProfiles(PROFILE_PRODUCTION);
		} else {
			environment.setActiveProfiles(PROFILE_LOCALHOST);
		}

	}

}
