package org.mlm.converters;

import java.util.Collection;

import org.mlm.model.entity.Role;
import org.mlm.model.entity.User;
import org.mlm.model.json.UserJTable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class UserToUserJTableConverter implements Converter<User, UserJTable> {

	public UserJTable convert(User user) {
		UserJTable userJTable = new UserJTable();
		userJTable.setUserId(user.getUserId());
		userJTable.setEmail(user.getEmail());
		userJTable.setEnabled(user.getEnabled());
		userJTable.setPassword(user.getPassword());
		userJTable.setUserName(user.getUserName());
		Collection<Role> roles = user.getRoles();
		for (Role role : roles) {
			if ("ROLE_USER".equals(role.getAuthority()))
				userJTable.setUserRole(true);
			if ("ROLE_ADMIN".equals(role.getAuthority()))
				userJTable.setAdminRole(true);
		}

		return userJTable;
	}

}
