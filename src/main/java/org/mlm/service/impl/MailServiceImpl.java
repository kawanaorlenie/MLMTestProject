package org.mlm.service.impl;

import org.mlm.model.dto.MailModel;
import org.mlm.model.entity.ForgotPasswordModel;
import org.mlm.model.entity.User;
import org.mlm.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private SimpleMailMessage simpleMailMessage;
	@Autowired
	private MailSender mailSender;

	public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
		this.simpleMailMessage = simpleMailMessage;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void send(MailModel mailModel) {		

		SimpleMailMessage message = new SimpleMailMessage(simpleMailMessage);
		message.setTo(mailModel.getTo());
		message.setText(mailModel.getText());
		mailSender.send(message);
	}

	public void sendActivationMail(User user , String uuid) {
		MailModel mailModel = new MailModel();
		mailModel.setTo(user.getEmail());
//		mailModel.setText("To activate click: http://localhost:8081/mlm/activation/?id="+uuid+"&userName="+userModel.getUserName());				
		mailModel.setText("To activate click: http://mlmtestproject.jaa4567.cloudbees.net/activation/?id="+uuid+"&userName="+user.getUserName());				
		
		send(mailModel);
	}

	public void sendForgotPasswordMail(ForgotPasswordModel forgotPasswordModel) {
		MailModel mailModel = new MailModel();
		mailModel.setTo(forgotPasswordModel.getEmail());
		mailModel.setText("To start password recovery click: http://mlmtestproject.jaa4567.cloudbees.net/reset/?id="
							+forgotPasswordModel.getUuid()+"&email="+forgotPasswordModel.getEmail());			
		
		send(mailModel);
		
	}

}
