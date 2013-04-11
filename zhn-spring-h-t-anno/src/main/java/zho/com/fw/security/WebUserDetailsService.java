/**
 * Classname :WebUserDetailsService.java
 *
 */
package zho.com.fw.security;

import javax.inject.Inject;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import zho.com.fw.info.AccountInfo;
import zho.com.fw.service.AccountService;

/**
*
* @author Ho.Viet.Duan	
* Created Jan 07, 2011
*/
public class WebUserDetailsService implements IWebUserDetailsService, InitializingBean {

	@Inject
	private AccountService accountService;

	/*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
     */
	public synchronized UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {
    	final String LOCATION = "loadUserByUsername(userName:" + userName + ")";
    		WebUserDetails user = null;
    		AccountInfo accountInfo = null;
    	try {
    		accountInfo = accountService.findAccountByUsername(userName);
    		if (accountInfo == null) {    			
    			System.out.println("User not found!!!");
    			throw new UsernameNotFoundException("User not found!!!");
    		}
    		user = new AppWebUserDetails(accountInfo, "ROLE_USER");
        } catch (UsernameNotFoundException ufe) {
        	System.out.println(ufe.getMessage());
            throw ufe;
        } catch (Exception e) {
        	System.out.println(e.getMessage());
            throw new UsernameNotFoundException("User not found!!!", e);
        }
        System.out.println(LOCATION + ":: END");
        return user;
    }

    /*
     * (non-Javadoc)
     * 
     * @see kr.ac.edu.lab.web.security.IWebUserDetailsService#reloadUserByUsername(java.lang.String)
     */
    public void reloadUserByUsername(String userName) {

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
    }
}
