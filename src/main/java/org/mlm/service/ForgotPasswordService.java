package org.mlm.service;

import org.mlm.model.entity.ForgotPasswordModel;

public interface ForgotPasswordService {
	
	public void start(ForgotPasswordModel forgotPasswordModel);
	public boolean finish(String id, String email);
	public boolean isCorrect(String id, String email);

}
