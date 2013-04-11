/*
 * Copyright 2013 the original author or authors.
 */
package zho.com.fw.info;

import java.io.Serializable;


public class AccountInfo implements Serializable {

	private Integer id;

	private String username;

	private String password;

	private String firstName;

	private String lastName;

	public AccountInfo() {
	}

	public AccountInfo(String username, String password, String firstName,
			String lastName) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
