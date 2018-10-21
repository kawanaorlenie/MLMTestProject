package org.mlm.controller;


import javax.validation.Valid;

import org.mlm.model.entity.ResetPasswordModel;
import org.mlm.service.ResetPasswordService;
import org.mlm.validator.ResetPasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/reset")
public class ResetPasswordController {

	@Autowired
	private ResetPasswordService resetPasswordService;
	@Autowired
	private ResetPasswordValidator resetPasswordValidator;

	@RequestMapping(method = RequestMethod.GET, params = { "id", "email" })
	public ModelAndView showForm(@RequestParam(value = "id") String id, @RequestParam(value = "email") String email) {

		if (resetPasswordService.isCorrect(id, email)) {
			ResetPasswordModel tempResetPasswordModel = new ResetPasswordModel();
			tempResetPasswordModel.setEmail(email);
			return new ModelAndView("reset-password", "resetPasswordModel", tempResetPasswordModel);
		} else
			return new ModelAndView("resetPassword");
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processRegistration(@Valid @ModelAttribute("resetPasswordModel") ResetPasswordModel resetPasswordModel, BindingResult result) {

		System.out.println(resetPasswordModel.getPassword() + "fg");
		resetPasswordValidator.validate(resetPasswordModel, result);
		if (result.hasErrors()) {
			return new ModelAndView("resetPassword", "resetPasswordModel", resetPasswordModel);
		} else {
			resetPasswordService.resetPassword(resetPasswordModel);
			return new ModelAndView("resetPassword");
		}
	}

}
