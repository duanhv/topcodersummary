package zho.com.fw.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import zho.com.fw.bean.CustomerBean;
import zho.com.fw.dao.CustomerDao;
import zho.com.fw.info.Customer;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

	@Autowired
	private CustomerDao customerDao;
	
	public void createCustomer(Customer customer) throws Exception {
		CustomerBean customerBean = new CustomerBean();
		BeanUtils.copyProperties(customer, customerBean);
		customerDao.createCustomer(customerBean);		
	}
	
	public Customer findCustomerByUsername(String username) throws Exception {
		CustomerBean account = null;
		account = customerDao.findCustomerByUsername(username);
		
		Customer accountInfo = new Customer();
		BeanUtils.copyProperties(account, accountInfo);
		return accountInfo;
	}
}
