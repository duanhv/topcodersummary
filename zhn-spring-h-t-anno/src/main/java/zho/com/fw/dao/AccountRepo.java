/*
 * Copyright 2013 the original author or authors.
 *
 */
package zho.com.fw.dao;


import zho.com.fw.bean.Account;
import zho.com.fw.exception.UsernameAlreadyInUseException;

public interface AccountRepo {
	
	void createAccount(Account account) throws UsernameAlreadyInUseException;

	public Account findAccountByUsername(String username) throws Exception;
	
}
