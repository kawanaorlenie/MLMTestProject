package org.mlm.validator;

import org.mlm.dao.UserDAO;
import org.mlm.model.dto.ChangePasswordModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component("changePasswordValidator")
public class ChangePasswordValidator {

	@Autowired
	private UserDAO userDAO;
	@Autowired
	private ShaPasswordEncoder shaPasswordEncoder;

	public boolean supports(Class<?> klass) {
		return ChangePasswordModel.class.isAssignableFrom(klass);
	}

	public void validate(String userName, Object target, Errors errors) {
		ChangePasswordModel changePasswordModel = (ChangePasswordModel) target;

		if (!(changePasswordModel.getNewPassword()).equals(changePasswordModel.getConfirmPassword())) {
			errors.rejectValue("confirmPassword", "MatchingPassword.changePasswordModel.confirmPassword");
		}

		if (!(shaPasswordEncoder.encodePassword(changePasswordModel.getOldPassword(), null)).equals(userDAO
				.findByUserName(userName).getPassword())) {
			errors.rejectValue("oldPassword", "MatchingPassword.changePasswordModel.oldPassword");
		}

	}
}
