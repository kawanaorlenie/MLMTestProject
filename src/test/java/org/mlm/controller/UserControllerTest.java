package org.mlm.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mlm.dao.RoleDAO;
import org.mlm.dao.UserDAO;
import org.mlm.service.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml",
		"file:src/main/webapp/WEB-INF/test-context.xml" })
@WebAppConfiguration
public class UserControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private UserService userServiceMock;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp() {
		Mockito.reset(userServiceMock);
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.build();
	}

	@Test
	public void registerUserNameInvalid() throws Exception {
		// String userName = "short";
		mockMvc.perform(get("/")).andExpect(status().isOk())
				.andExpect(view().name("login"));
//				.andExpect(forwardedUrl("/WEB-INF/views/login/login.jsp"));
		// .andExpect(model().attributeHasFieldErrors("user", "userName"));
		 verifyZeroInteractions(userServiceMock);
	}

	// public void
	// add_DescriptionAndTitleAreTooLong_ShouldRenderFormViewAndReturnValidationErrorsForTitleAndDescription()
	// throws Exception {
	// String title = TestUtil.createStringWithLength(101);
	// String description = TestUtil.createStringWithLength(501);
	//
	// mockMvc.perform(
	// post("/todo/add")
	// .contentType(MediaType.APPLICATION_FORM_URLENCODED)
	// .param("description", description)
	// .param("title", title)
	// .sessionAttr("todo", new TodoDTO()))
	// .andExpect(status().isOk())
	// .andExpect(view().name("todo/add"))
	// .andExpect(forwardedUrl("/WEB-INF/jsp/todo/add.jsp"))
	// .andExpect(model().attributeHasFieldErrors("todo", "title"))
	// .andExpect(
	// model().attributeHasFieldErrors("todo", "description"))
	// .andExpect(
	// model().attribute("todo",
	// hasProperty("id", nullValue())))
	// .andExpect(
	// model().attribute("todo",
	// hasProperty("description", is(description))))
	// .andExpect(
	// model().attribute("todo",
	// hasProperty("title", is(title))));
	//
	// verifyZeroInteractions(todoServiceMock);
	// }

}
