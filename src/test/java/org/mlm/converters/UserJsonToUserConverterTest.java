package org.mlm.converters;

import org.junit.runner.RunWith;
import org.mlm.dao.RoleDAO;
import org.mockito.InjectMocks;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml",
		"file:src/main/webapp/WEB-INF/test-context.xml" })
public class UserJsonToUserConverterTest {

	@InjectMocks
	private RoleDAO roleDAO;

}
