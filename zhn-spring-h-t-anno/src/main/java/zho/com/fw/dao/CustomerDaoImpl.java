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

import zho.com.fw.bean.CustomerBean;
import zho.com.fw.exception.UsernameAlreadyInUseException;

@Repository
public class CustomerDaoImpl 
	extends HibernateDaoSupport implements CustomerDao {

	@Autowired
	public CustomerDaoImpl(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	public void createCustomer(CustomerBean user) throws UsernameAlreadyInUseException {
		try {
			getHibernateTemplate().save(user);
		} catch (DuplicateKeyException e) {
			throw new UsernameAlreadyInUseException(user.getUsername());
		}
	}

	public CustomerBean findCustomerByUsername(String username) {
		String queryString = "from CustomerBean as model where model.username = ?";
		return (CustomerBean)getHibernateTemplate().find(queryString, username).get(0);
	}

}
