package org.mlm.service;

import org.mlm.model.entity.ResetPasswordModel;

public interface ResetPasswordService {

	boolean isCorrect(String id, String email);

	void resetPassword(ResetPasswordModel resetPasswordModel);

}
