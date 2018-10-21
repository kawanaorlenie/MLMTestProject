package org.mlm.controller;

import javax.validation.Valid;

import org.mlm.model.entity.User;
import org.mlm.model.json.GeneralJson;
import org.mlm.service.UserService;
import org.mlm.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ValidationController {
	
	@Autowired
	UserService userService;
	@Autowired
	private ValidationService validationService;
	
	@RequestMapping(value="/validation/userName", method = RequestMethod.POST)
	public @ResponseBody GeneralJson validateUserName(@Valid User user, BindingResult result){
		
		if(userService.exists(user.getUserName())){
			result.rejectValue("userName", "Availability.user.userName", "User Name already exists");
		}
		
		if(result.hasFieldErrors("userName")){
			return new GeneralJson("ERROR",validationService.createMassages(result), "Validation error");
					//new Availability(false,result.getFieldErrors("userName"));
		}
		else
			return new GeneralJson("OK", null, "User name is available");
		
	}

}
