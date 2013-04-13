package zho.com.fw.service;

import zho.com.fw.info.Customer;

public interface AccountService {
	
	public void createCustomer(Customer customer) throws Exception;
	
	public Customer findCustomerByUsername(String username) throws Exception;
		
}
