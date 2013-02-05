package com.awrank.web.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;

import javax.sql.DataSource;

/**
 * @author Andrew Stoyaltsev
 */
@Configuration
public class SocialConfig {

	@Autowired
	private Environment env;

	@Autowired
	private DataSource dataSource;

	/*
	@Bean
	public ConnectionFactoryLocator connectionFactoryLocator() {
		ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
		registry.addConnectionFactory(new FacebookConnectionFactory(
				env.getProperty("social.facebook.clientId"),
				env.getProperty("social.facebook.clientSecret")));

		return registry;
	}

	@Bean
	public UsersConnectionRepository usersConnectionRepository() {
		JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource,
				connectionFactoryLocator(), Encryptors.noOpText());
		repository.setConnectionSignUp(new SimpleConnectionSignUp());
		return repository;
	}

	@Bean
	@Scope(value="request", proxyMode= ScopedProxyMode.INTERFACES)
	public ConnectionRepository connectionRepository() {
		User user = SecurityContext.getCurrentUser();
		return usersConnectionRepository().createConnectionRepository(user.getId());
	}

	@Bean
	@Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)
	public Facebook facebook() {
		return connectionRepository().getPrimaryConnection(Facebook.class).getApi();
	}

	@Bean
	public ProviderSignInController providerSignInController() {
		return new ProviderSignInController(connectionFactoryLocator(), usersConnectionRepository(),
				new SimpleSignInAdapter());
	}
*/
}
