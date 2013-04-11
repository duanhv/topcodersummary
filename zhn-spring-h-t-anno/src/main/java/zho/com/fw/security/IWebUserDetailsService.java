/**
 * Classname :IWebUserDetailsService.java

 */
package zho.com.fw.security;

import org.springframework.security.core.userdetails.UserDetailsService;

/**
*
* @author Ho.Viet.Duan	
*/
public interface IWebUserDetailsService extends UserDetailsService {
    /**
     * 
     * @param userName
     */
    public void reloadUserByUsername(String userName);
}
