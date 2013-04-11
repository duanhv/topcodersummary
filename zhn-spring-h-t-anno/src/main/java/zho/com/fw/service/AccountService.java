package zho.com.fw.service;

import zho.com.fw.bean.Account;

public interface AccountService {
	
	public void addAccount(Account account) throws Exception;
	
	public Account findAccountByUsername(String username) throws Exception;
		
}
