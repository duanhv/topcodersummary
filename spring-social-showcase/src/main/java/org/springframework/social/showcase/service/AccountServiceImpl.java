package org.springframework.social.showcase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.showcase.bean.Account;
import org.springframework.social.showcase.dao.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	public void addAccount(Account account) throws Exception {
		accountRepository.createAccount(account);
	}
	
	public Account findAccountByUsername(String username) throws Exception {
		Account account = null;
		accountRepository.findAccountByUsername(username);
		return account;
	}
}
