package org.mlm.service;

import org.mlm.model.dto.MailModel;
import org.mlm.model.entity.ForgotPasswordModel;
import org.mlm.model.entity.User;

public interface MailService {
	
	public void send(MailModel mailModel);
	public void sendActivationMail(User user, String uuid);
	public void sendForgotPasswordMail(ForgotPasswordModel forgotPasswordModel);

}
