/*
 * Copyright 2013 the original author or authors.
 *
 */
package zho.com.fw.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableJdbcConnectionRepository;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.ReconnectFilter;
import org.springframework.social.facebook.config.annotation.EnableFacebook;
import org.springframework.social.facebook.web.DisconnectController;
import org.springframework.social.linkedin.config.annotation.EnableLinkedIn;
import org.springframework.social.twitter.config.annotation.EnableTwitter;

import zho.com.fw.controller.signin.SimpleSignInAdapter;
import zho.com.fw.social.facebook.PostToWallAfterConnectInterceptor;
import zho.com.fw.social.twitter.TweetAfterConnectInterceptor;

/**
 * Spring Social Configuration.
 * 
 * This configuration is demonstrating the use of the simplified Spring Social configuration options from Spring Social 1.1.
 * It will only be used when the "simple" profile is active.
 * The more complex/explicit configuration is still available in ExplicitSocialConfig.java and will be used if the "explicit" profile is active.
 * 
 */
@Configuration
@Profile("simple")
@EnableJdbcConnectionRepository
@EnableTwitter(appId="${twitter.consumerKey}", appSecret="${twitter.consumerSecret}")
@EnableFacebook(appId="${facebook.clientId}", appSecret="${facebook.clientSecret}")
@EnableLinkedIn(appId="${linkedin.consumerKey}", appSecret="${linkedin.consumerSecret}")
public class SocialConfig {

	@Inject
	private Environment environment;
	
	@Inject
	private ConnectionFactoryLocator connectionFactoryLocator;
	
	@Inject 
	private ConnectionRepository connectionRepository;

	@Inject 
	private UsersConnectionRepository usersConnectionRepository;

	@Bean
	public ConnectController connectController() {
		ConnectController connectController = new ConnectController(connectionFactoryLocator, connectionRepository);
		connectController.addInterceptor(new PostToWallAfterConnectInterceptor());
		connectController.addInterceptor(new TweetAfterConnectInterceptor());
		return connectController;
	}

	@Bean
	public ProviderSignInController providerSignInController(RequestCache requestCache) {
		return new ProviderSignInController(connectionFactoryLocator, usersConnectionRepository, new SimpleSignInAdapter(requestCache));
	}
	
	@Bean
	public DisconnectController disconnectController() {
		return new DisconnectController(usersConnectionRepository, environment.getProperty("facebook.clientSecret"));
	}

	/*
	 * ReconnectFilter only available in latest 1.1.0.BUILD-SNAPSHOT builds.
	 * This comment will be removed when Spring Social 1.1.0.M3 is released.
	 */
	@Bean
	public ReconnectFilter apiExceptionHandler() {
		return new ReconnectFilter(usersConnectionRepository, userIdSource());
	}
	
	@Bean
	public UserIdSource userIdSource() {
		return new UserIdSource() {			
			@Override
			public String getUserId() {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				if (authentication == null) {
					throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");
				}
				return authentication.getName();
			}
		};
	}

}
