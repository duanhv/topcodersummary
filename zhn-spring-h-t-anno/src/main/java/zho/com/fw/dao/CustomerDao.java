/*
 * Copyright 2013 the original author or authors.
 *
 */
package zho.com.fw.dao;


import zho.com.fw.bean.CustomerBean;
import zho.com.fw.exception.UsernameAlreadyInUseException;

public interface CustomerDao {
	
	void createCustomer(CustomerBean account) throws UsernameAlreadyInUseException;

	public CustomerBean findCustomerByUsername(String username) throws Exception;
	
}
