package org.mlm.model.json;

import org.mlm.model.entity.Role;
import org.mlm.model.entity.User;

public class UserJTable {

	private int userId;
	private String userName;
	private String password;
	private String email;
	private boolean enabled;
	private boolean userRole = false;
	private boolean adminRole = false;

	public boolean isUserRole() {
		return userRole;
	}

	public void setUserRole(boolean userRole) {
		this.userRole = userRole;
	}

	public boolean isAdminRole() {
		return adminRole;
	}

	public void setAdminRole(boolean adminRole) {
		this.adminRole = adminRole;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public UserJTable() {
	}

	public UserJTable(User user) {
		this.userId = user.getUserId();
		this.email = user.getEmail();
		this.enabled = user.getEnabled();
		this.password = user.getPassword();
		this.userName = user.getUserName();

		for (Role r : user.getRoles()) {
			if ("ROLE_USER".equals(r.getAuthority()))
				this.userRole = true;
			if ("ROLE_ADMIN".equals(r.getAuthority()))
				this.adminRole = true;
		}
	}

}
