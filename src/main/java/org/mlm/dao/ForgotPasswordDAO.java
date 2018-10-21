package org.mlm.dao;

import org.mlm.model.entity.ForgotPasswordModel;
import org.springframework.data.repository.CrudRepository;

public interface ForgotPasswordDAO extends CrudRepository<ForgotPasswordModel, String> {
}
