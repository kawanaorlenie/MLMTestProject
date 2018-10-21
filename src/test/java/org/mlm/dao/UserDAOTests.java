package org.mlm.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mlm.config.InfrastructureContextConfiguration;
import org.mlm.config.TestDataContextConfiguration;
import org.mlm.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { InfrastructureContextConfiguration.class,
		TestDataContextConfiguration.class })
@Transactional
public class UserDAOTests {

	@Autowired
	private UserDAO userDAO;

	private User user;

	@Before
	public void setupData() {
		user = new User();
		user.setUserName("user00");

	}

	@Test
	public void testFindById() {
		User user = userDAO.findOne(this.user.getUserId());
		assertEquals(this.user.getUserName(), user.getUserName());
	}
}
