/*
 * Copyright 2013 the original author or authors.
 *
 */
package zho.com.fw.exception;

public class UsernameAlreadyInUseException extends Exception {
	public UsernameAlreadyInUseException(String username) {
		super("The username '" + username + "' is already in use.");
	}
}
