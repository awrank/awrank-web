package com.awrank.web.backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

/**
 *
 */
public class EnvironmentInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	private static Logger LOG = LoggerFactory.getLogger(EnvironmentInitializer.class);

	public void initialize(ConfigurableApplicationContext ctx) {
		ConfigurableEnvironment environment = ctx.getEnvironment();
		try {
			environment.getPropertySources()
					.addFirst(new ResourcePropertySource("classpath:props/boot.properties"));
			LOG.info("props/boot.properties loaded");
		} catch (IOException e) {
			// it's ok if the file is not there. we will just log that info.
			LOG.info("didn't find props/boot.properties in classpath so not loading it in the AppContextInitialized");
		}

		String property = environment.getProperty("build.production.profile");
		Boolean isProduction = Boolean.parseBoolean(property);
		System.out.println("build.production.profile=" + property);
		if (isProduction) {
			environment.setActiveProfiles(ProdConfiguration.PROFILE_PROD);
		} else {
			environment.setActiveProfiles(DevConfiguration.PROFILE_DEV);
		}
		//ctx.refresh();
	}

}
