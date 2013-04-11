package zho.com.fw.service;

import zho.com.fw.bean.Account;
import zho.com.fw.info.AccountInfo;

public interface AccountService {
	
	public void addAccount(Account account) throws Exception;
	
	public AccountInfo findAccountByUsername(String username) throws Exception;
		
}
