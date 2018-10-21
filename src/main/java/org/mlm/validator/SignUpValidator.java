package org.mlm.validator;

import org.mlm.dao.UserDAO;
import org.mlm.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component("signUpValidator")
public class SignUpValidator {

	@Autowired
	private UserDAO userDAO;

	public boolean supports(Class<?> klass) {
		return User.class.isAssignableFrom(klass);
	}

	public void validate(Object target, Errors errors) {
		User signUpModel = (User) target;

		if (!(signUpModel.getPassword()).equals(signUpModel.getConfirmPassword()))
			errors.rejectValue("confirmPassword", "MatchingPassword.signUpModel.password");

		if (userDAO.findByEmail(signUpModel.getEmail()) != null)
			errors.rejectValue("email", "Email.unAvailable");

		if (userDAO.findByUserName(signUpModel.getUserName()) != null)
			errors.rejectValue("userName", "Availability.signUpModel.userName");
	}
}