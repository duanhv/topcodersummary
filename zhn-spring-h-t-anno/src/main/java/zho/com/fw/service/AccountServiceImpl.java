package zho.com.fw.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import zho.com.fw.bean.Account;
import zho.com.fw.dao.AccountRepo;
import zho.com.fw.info.AccountInfo;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepo accountRepository;
	
	public void addAccount(Account account) throws Exception {
		
		accountRepository.createAccount(account);
		
	}
	
	public AccountInfo findAccountByUsername(String username) throws Exception {
		Account account = null;
		account = accountRepository.findAccountByUsername(username);
		
		AccountInfo accountInfo = new AccountInfo();
		BeanUtils.copyProperties(account, accountInfo);
		return accountInfo;
	}
}
