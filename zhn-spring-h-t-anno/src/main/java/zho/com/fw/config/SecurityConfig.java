/*
 * Copyright 2013 the original author or authors.
 *
 */
package zho.com.fw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import zho.com.fw.security.WebUserDetailsService;

/**
 * Security Configuration.
 */
@Configuration
@ImportResource("classpath:zho/com/fw/config/security.xml")
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
    	return NoOpPasswordEncoder.getInstance();
	}

	@Bean
	public TextEncryptor textEncryptor() {
		return Encryptors.noOpText();
	}

	@Bean
	public WebUserDetailsService authenticationService() {
		return new WebUserDetailsService();
	}
	
}
