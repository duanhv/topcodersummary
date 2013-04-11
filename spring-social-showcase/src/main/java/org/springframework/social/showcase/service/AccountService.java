package org.springframework.social.showcase.service;

import org.springframework.social.showcase.bean.Account;

public interface AccountService {
	
	public void addAccount(Account account) throws Exception;
	
	public Account findAccountByUsername(String username) throws Exception;
		
}
