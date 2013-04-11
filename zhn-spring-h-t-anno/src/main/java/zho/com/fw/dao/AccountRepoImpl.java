/*
 * Copyright 2013 the original author or authors.
 *
 */
package zho.com.fw.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import zho.com.fw.bean.Account;
import zho.com.fw.exception.UsernameAlreadyInUseException;

@Repository
public class AccountRepoImpl 
	extends HibernateDaoSupport implements AccountRepo {

	@Autowired
	public AccountRepoImpl(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	public void createAccount(Account user) throws UsernameAlreadyInUseException {
		try {
			getHibernateTemplate().save(user);
		} catch (DuplicateKeyException e) {
			throw new UsernameAlreadyInUseException(user.getUsername());
		}
	}

	public Account findAccountByUsername(String username) {
		String queryString = "from Account as model where model.username = ?";
		return (Account)getHibernateTemplate().find(queryString, username).get(0);
	}

}
