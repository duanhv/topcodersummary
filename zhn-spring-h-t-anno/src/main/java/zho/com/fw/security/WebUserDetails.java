/**
 * Classname :AccountModel.java
 *
 * Version information: 1.0
 */

package zho.com.fw.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public abstract class WebUserDetails implements UserDetails {

	private final Map<String, Serializable> attrs = new HashMap<String, Serializable>();
	protected List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public final boolean addAttr(String key, Serializable value) {
		if(key != null && value != null) {
			attrs.put(key, value);
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public final Serializable getAttr(String key) {
		if(key != null) {
			return attrs.get(key);
		}
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public final Set<String> getAttrKeys() {
		return attrs.keySet();
	}
	
	/**
	 * 
	 * @return
	 */
	public abstract String getUserId();
	
    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.userdetails.UserDetails#getAuthorities()
     */
    public List<GrantedAuthority> getAuthorities() {
        return roles;
    }
}
