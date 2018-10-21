package org.mlm.dao;

import org.mlm.dao.custom.UserDAOCustom;
import org.mlm.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface UserDAO extends JpaRepository<User, Integer>,
		QueryDslPredicateExecutor<User>, UserDAOCustom {

	public User findByUserName(String name);

	// public boolean updatePassword(String newPassword, String userName);
	public User findByEmail(String email);

}
