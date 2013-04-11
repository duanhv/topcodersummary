/*
 * Copyright 2013 the original author or authors.
 */
package zho.com.fw.config;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.facebook.web.DisconnectController;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.connect.LinkedInConnectionFactory;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

import zho.com.fw.controller.signin.SimpleSignInAdapter;
import zho.com.fw.social.facebook.PostToWallAfterConnectInterceptor;
import zho.com.fw.social.twitter.TweetAfterConnectInterceptor;

/**
 * Spring Social Configuration.
 * 
 * This configuration shows an explicit configuration of Spring Social beans, not using the simplied configuration options from Spring Social 1.1.
 * It will only be used when the "explicit" profile is active.
 * The simpler configuration using Spring Social 1.1 annotation is available in SocialConfig.java.
 * 
 */
@Configuration
@Profile("explicit")
public class ExplicitSocialConfig {

	@Inject
	private Environment environment;

	@Inject
	private DataSource dataSource;

	@Bean
	@Scope(value="singleton", proxyMode=ScopedProxyMode.INTERFACES) 
	public ConnectionFactoryLocator connectionFactoryLocator() {
		ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
		registry.addConnectionFactory(new TwitterConnectionFactory(environment.getProperty("twitter.consumerKey"),
				environment.getProperty("twitter.consumerSecret")));
		registry.addConnectionFactory(new FacebookConnectionFactory(environment.getProperty("facebook.clientId"),
				environment.getProperty("facebook.clientSecret"), environment.getProperty("facebook.appNamespace")));
		registry.addConnectionFactory(new LinkedInConnectionFactory(environment.getProperty("linkedin.consumerKey"),
				environment.getProperty("linkedin.consumerSecret")));
		return registry;
	}

	@Bean
	@Scope(value="singleton", proxyMode=ScopedProxyMode.INTERFACES) 
	public UsersConnectionRepository usersConnectionRepository() {
		return new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator(), Encryptors.noOpText());
	}

	@Bean
	@Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)	
	public ConnectionRepository connectionRepository() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");
		}
		return usersConnectionRepository().createConnectionRepository(authentication.getName());
	}

	@Bean
	@Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)	
	public Facebook facebook() {
		Connection<Facebook> facebook = connectionRepository().findPrimaryConnection(Facebook.class);
		return facebook != null ? facebook.getApi() : new FacebookTemplate();
	}
	
	@Bean
	@Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)	
	public Twitter twitter() {
		Connection<Twitter> twitter = connectionRepository().findPrimaryConnection(Twitter.class);
		return twitter != null ? twitter.getApi() : new TwitterTemplate();
	}

	@Bean
	@Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)	
	public LinkedIn linkedin() {
		Connection<LinkedIn> linkedin = connectionRepository().findPrimaryConnection(LinkedIn.class);
		return linkedin != null ? linkedin.getApi() : null;
	}

	@Bean
	public ConnectController connectController() {
		ConnectController connectController = new ConnectController(connectionFactoryLocator(), connectionRepository());
		connectController.addInterceptor(new PostToWallAfterConnectInterceptor());
		connectController.addInterceptor(new TweetAfterConnectInterceptor());
		return connectController;
	}

	@Bean
	public ProviderSignInController providerSignInController(RequestCache requestCache) {
		return new ProviderSignInController(connectionFactoryLocator(), usersConnectionRepository(), new SimpleSignInAdapter(requestCache));
	}
	
	@Bean
	public DisconnectController disconnectController() {
		return new DisconnectController(usersConnectionRepository(), environment.getProperty("facebook.clientSecret"));
	}

}
