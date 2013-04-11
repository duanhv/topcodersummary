package zho.com.fw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zho.com.fw.bean.Account;
import zho.com.fw.dao.AccountRepo;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepo accountRepository;
	
	public void addAccount(Account account) throws Exception {
		accountRepository.createAccount(account);
	}
	
	public Account findAccountByUsername(String username) throws Exception {
		Account account = null;
		account = accountRepository.findAccountByUsername(username);
		return account;
	}
}
