package org.mlm.validator;

import org.mlm.dao.UserDAO;
import org.mlm.model.entity.ForgotPasswordModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class ForgotPasswordValidator {

	@Autowired
	private UserDAO userDAO;

	public void validate(ForgotPasswordModel forgotPasswordModel, BindingResult result) {

		if (userDAO.findByEmail(forgotPasswordModel.getEmail()) == null)
			result.rejectValue("email", "NotExist.email");

	}

}
