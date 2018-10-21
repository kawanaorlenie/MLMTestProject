package org.mlm.model.entity;

import javax.validation.constraints.Size;

public class ResetPasswordModel {

	@Size(min = 8, max = 20)
	private String password;
	private String confirmPassword;
	private String email;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
