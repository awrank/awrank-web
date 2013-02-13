package com.awrank.web.backend.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.inject.Inject;
import javax.sql.DataSource;

/**
 * Configuration for production environment.
 *
 * @author Andrew Stoyaltsev
 */

@Configuration
@Profile("production")
@PropertySource("classpath:com/awrank/web/backend/config/production.properties")
public class ProductionConfiguration extends LocalhostConfiguration {

	/* inherited from localhost configuration
	@Inject
	Environment env;

	@Bean
	public DataSource dataSource() {
		DataSource dataSource = new BasicDataSource();
		((BasicDataSource) dataSource).setDriverClassName(env.getProperty("jdbc.driverClassName"));
		((BasicDataSource) dataSource).setUrl(env.getProperty("jdbc.url"));
		((BasicDataSource) dataSource).setUsername(env.getProperty("jdbc.username"));
		((BasicDataSource) dataSource).setPassword(env.getProperty("jdbc.password"));
		return dataSource;
	}
	*/

}
