package org.mlm.controller;

import org.mlm.model.entity.User;
import org.mlm.model.json.JTableJson;
import org.mlm.model.json.UserJTable;
import org.mlm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserManagementController {

	@Autowired
	private UserService userService;
	@Autowired
	private ConversionService conversionService;

	@RequestMapping(value = "/list")
	@ResponseBody
	public JTableJson listUsers() {
		return new JTableJson("OK", userService.getAllJTableUsers(), null);
	}

	@RequestMapping(value = "/create")
	@ResponseBody
	public JTableJson createUser(@ModelAttribute UserJTable userJson) {
		User userModel = conversionService.convert(userJson, User.class);
		userService.createUser(userModel);
		return new JTableJson("OK", userJson, "");
	}

	@RequestMapping(value = "/update")
	@ResponseBody
	public JTableJson updateUser(@ModelAttribute UserJTable userJson) {
		User userModel = conversionService.convert(userJson, User.class);
		userService.updateUser(userModel);
		return new JTableJson("OK", userJson, null);
	}

	@RequestMapping(value = "/delete")
	@ResponseBody
	public JTableJson deleteUser(@ModelAttribute UserJTable userJson) {
		User userModel = conversionService.convert(userJson, User.class);
		userService.deleteUser(userModel);
		return new JTableJson("OK", null, null);
	}
}
