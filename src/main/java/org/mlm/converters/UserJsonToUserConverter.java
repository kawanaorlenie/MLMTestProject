package org.mlm.converters;

import java.util.ArrayList;
import java.util.List;

import org.mlm.dao.RoleDAO;
import org.mlm.model.entity.Role;
import org.mlm.model.entity.User;
import org.mlm.model.json.UserJTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class UserJsonToUserConverter implements Converter<UserJTable, User> {
	
	@Autowired
	private RoleDAO roleDAO;

	public User convert(UserJTable userJson) {
		User userModel = new User();
		userModel.setEmail(userJson.getEmail());
		userModel.setEnabled(userJson.isEnabled());
		userModel.setPassword(userJson.getPassword());
		userModel.setUserId(userJson.getUserId());
		userModel.setUserName(userJson.getUserName());
		List<Role> roles = new ArrayList<Role>();
		if (userJson.isUserRole())
			roles.add(roleDAO.findByAuthority("ROLE_USER"));
		if (userJson.isAdminRole())
			roles.add(roleDAO.findByAuthority("ROLE_ADMIN"));
		userModel.setRoles(roles);
		return userModel;
	}

}
