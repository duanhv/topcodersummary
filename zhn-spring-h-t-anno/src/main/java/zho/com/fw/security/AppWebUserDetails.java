/**
 * Classname :AccountModel.java
 *
 * Version information: 1.0
 */

package zho.com.fw.security;

import java.util.ArrayList;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import zho.com.fw.info.AccountInfo;


/**
*
* @author Ho.Viet.Duan	
*/
@SuppressWarnings("serial")
public class AppWebUserDetails extends WebUserDetails {
	public interface Keys {
		String USER_ATTR = "USER";
	}
	
	public AppWebUserDetails(AccountInfo accountInfo, String role) {
        if (accountInfo != null) {
            // +++
        	super.addAttr(Keys.USER_ATTR, accountInfo);
            
            // +++ add roles
            if (!StringUtils.isEmpty(role)) {
            	if (this.roles == null) {
            		this.roles = new ArrayList<GrantedAuthority>();
            	}
                this.roles.add(new SimpleGrantedAuthority(role));
            }            
        }
    }

    /**
     * 
     * @return
     */
    public AccountInfo getUser() {
    	try {
    		return (AccountInfo)this.getAttr(Keys.USER_ATTR);
    	} catch (Exception e) {
    		return null;
    	}
    }
    
	/* (non-Javadoc)
	 * @see org.springframework.security.userdetails.UserDetails#getPassword()
	 */
	@Override
	public String getPassword() {
		AccountInfo accountInfo = this.getUser();
		return (accountInfo != null ? accountInfo.getPassword() : null);
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.userdetails.UserDetails#getUsername()
	 */
	@Override
	public String getUsername() {
		AccountInfo accountInfo = this.getUser();
		return (accountInfo != null ? accountInfo.getUsername() : null);
	}

	/* 
	 * Indicates whether the user's account has expired. An expired account
     * cannot be authenticated. true if the user's account is valid (ie
     * non-expired), false if no longer valid (ie expired)
     * 
     * (non-Javadoc)
	 * @see org.springframework.security.userdetails.UserDetails#isAccountNonExpired()
	 */
	@Override
	public boolean isAccountNonExpired() {
		boolean isNonExpired = true;

        return isNonExpired;
	}

	/* 
	 * Indicates whether the user is locked or unlocked. A locked user cannot be
     * authenticated. true if the user is not locked, false otherwise
     * 
     * (non-Javadoc)
	 * @see org.springframework.security.userdetails.UserDetails#isAccountNonLocked()
	 */
	@Override
	public boolean isAccountNonLocked() {
		
		boolean nonLocked = true;
		
        return nonLocked;
	}

	/* 
	 * Indicates whether the user's credentials (password) has expired. Expired
     * credentials prevent authentication. true if the user's credentials are
     * valid (ie non-expired), false if no longer valid (ie expired)
     * 
     * (non-Javadoc)
     * @see org.springframework.security.userdetails.UserDetails#isCredentialsNonExpired()
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		 return true;
	}

	/* 
	 * Indicates whether the user is enabled or disabled. A disabled user cannot
     * be authenticated. true if the user is enabled, false otherwise
     * 
     * (non-Javadoc)
	 * @see org.springframework.security.userdetails.UserDetails#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		
		boolean enable = true;
		
		return enable;
	}

	@Override
	public String getUserId() {
		// TODO Auto-generated method stub
		AccountInfo accountInfo = this.getUser();
		return (accountInfo != null ? String.valueOf(accountInfo.getId()) : null);
	}
}
