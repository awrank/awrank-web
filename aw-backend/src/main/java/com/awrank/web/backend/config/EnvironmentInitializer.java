package com.awrank.web.backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

/**
 * Class provides loading of boot properties and selection of active profile based on
 * loaded properties.
 *
 * @author Andrew Stoyaltsev
 */
public class EnvironmentInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static Logger LOG = LoggerFactory.getLogger(EnvironmentInitializer.class);

    public void initialize(ConfigurableApplicationContext ctx) {
        ConfigurableEnvironment environment = ctx.getEnvironment();
        try {
            environment.getPropertySources().addFirst(new ResourcePropertySource("classpath:props/boot.properties"));
            LOG.info("Project properties props/boot.properties has been successfully loaded");
        } catch (IOException e) {
            // it's ok if the file is not there. we will just log that info.
            LOG.warn("Project properties props/boot.properties was not find in classpath and was not loaded. " +
                    "Your project will be built with default profile: DEV");
        }

        String property = environment.getProperty("build.production.profile");
        Boolean isProduction = Boolean.parseBoolean(property);
        String profileName;
        if (isProduction) {
            environment.setActiveProfiles(ProdConfiguration.PROFILE_PROD);
            profileName = ProdConfiguration.PROFILE_PROD;
        } else {
            environment.setActiveProfiles(DevConfiguration.PROFILE_DEV);
            profileName = ProdConfiguration.PROFILE_DEV;
        }
        LOG.debug("The project is building with " + profileName.toUpperCase() + " profile.");
    }

}
