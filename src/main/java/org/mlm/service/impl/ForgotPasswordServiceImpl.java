package org.mlm.service.impl;

import java.util.UUID;

import org.mlm.dao.ForgotPasswordDAO;
import org.mlm.model.entity.ForgotPasswordModel;
import org.mlm.service.ForgotPasswordService;
import org.mlm.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

	@Autowired
	private ForgotPasswordDAO forgotPasswordDAO;
	@Autowired
	private MailService mailService;

	public void start(ForgotPasswordModel forgotPasswordModel) {
		forgotPasswordDAO.delete(forgotPasswordModel.getEmail());
		forgotPasswordModel.setUuid(UUID.randomUUID().toString());
		forgotPasswordDAO.save(forgotPasswordModel);
		mailService.sendForgotPasswordMail(forgotPasswordModel);
	}

	public boolean finish(String id, String email) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isCorrect(String id, String email) {
		// TODO Auto-generated method stub
		return false;
	}

}
