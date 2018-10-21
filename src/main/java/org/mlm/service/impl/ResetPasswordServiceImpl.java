package org.mlm.service.impl;

import org.mlm.dao.ForgotPasswordDAO;
import org.mlm.dao.UserDAO;
import org.mlm.model.entity.ForgotPasswordModel;
import org.mlm.model.entity.ResetPasswordModel;
import org.mlm.model.entity.User;
import org.mlm.service.ResetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResetPasswordServiceImpl implements ResetPasswordService {

	@Autowired
	private ForgotPasswordDAO forgotPasswordDAO;
	@Autowired
	private UserDAO userDAO;

	public boolean isCorrect(String id, String email) {
		if (id == null || email==null)
			return false;
		ForgotPasswordModel forgotPasswordModel = forgotPasswordDAO.findOne(email);
		return id.equals(forgotPasswordModel.getUuid());
	}

	@Transactional
	public void resetPassword(ResetPasswordModel resetPasswordModel) {
		User userModel = userDAO.findByEmail(resetPasswordModel.getEmail());
		ShaPasswordEncoder crypto = new ShaPasswordEncoder();	
		userModel.setPassword(crypto.encodePassword(resetPasswordModel.getPassword(), null));
		userDAO.save(userModel);
		forgotPasswordDAO.delete(resetPasswordModel.getEmail());
		
	}

}
