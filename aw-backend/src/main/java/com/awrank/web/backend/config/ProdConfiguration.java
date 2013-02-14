package com.awrank.web.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 * Configuration for production environment.
 *
 * @author Andrew Stoyaltsev
 */

@Configuration
@Profile("prod")
@PropertySource("classpath:props/prod/app.properties")
public class ProdConfiguration extends DevConfiguration {

	public static final String PROFILE_PROD = "prod";

}
