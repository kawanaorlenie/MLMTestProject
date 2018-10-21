package org.mlm.dao.impl;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.apache.log4j.Logger;
import org.mlm.dao.custom.UserDAOCustom;
import org.mlm.model.entity.QRole;
import org.mlm.model.entity.QUser;
import org.mlm.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.impl.JPAQuery;

@Repository
public class UserDAOImpl implements UserDAOCustom {

	private static final Logger logger = Logger.getLogger(UserDAOImpl.class);

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	public List<User> getAllWithRoles() {
		logger.info("get all users with roles");
		JPAQuery query = new JPAQuery(
				entityManagerFactory.createEntityManager());
		List<User> users = query.from(QUser.user)
				.leftJoin(QUser.user.roles, QRole.role).fetch()
				.list(QUser.user);
		logger.info("number of users: " + users.size());
		return users;
	}

}
