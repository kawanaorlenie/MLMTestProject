package org.mlm.validator;

import org.mlm.model.entity.ResetPasswordModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class ResetPasswordValidator {

	public boolean supports(Class<?> klass) {
		return ResetPasswordModel.class.isAssignableFrom(klass);
	}

	public void validate(Object target, Errors errors) {
		
		ResetPasswordModel resetPasswordModel = (ResetPasswordModel) target;
		System.out.println("asdf");

		if (!(resetPasswordModel.getPassword()).equals(resetPasswordModel.getConfirmPassword())) {
			errors.rejectValue("confirmPassword", "MatchingPassword");

		}
	}

}
