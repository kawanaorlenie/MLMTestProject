package org.mlm.dao.custom;

import java.util.List;

import org.mlm.model.entity.User;

public interface UserDAOCustom {

	public List<User> getAllWithRoles();

}
