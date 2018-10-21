package org.mlm.controller;

import javax.validation.Valid;

import org.mlm.model.entity.ForgotPasswordModel;
import org.mlm.model.entity.User;
import org.mlm.service.ForgotPasswordService;
import org.mlm.service.UserService;
import org.mlm.validator.ForgotPasswordValidator;
import org.mlm.validator.SignUpValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

	@Autowired
	private SignUpValidator signUpValidator;
	@Autowired
	private UserService userService;
	@Autowired
	private ForgotPasswordValidator forgotPasswordValidator;
	@Autowired
	private ForgotPasswordService forgotPasswordService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView login(ModelMap model) {
		if (!model.containsAttribute("signUpModel"))
			model.addAttribute("signUpModel", new User());
		if (!model.containsAttribute("forgotPasswordModel"))
			model.addAttribute("forgotPasswordModel", new ForgotPasswordModel());
		return new ModelAndView("login", model);
	}

	@RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
	public ModelAndView loginerror(RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("error", "true");
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout() {
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ModelAndView register(@Valid @ModelAttribute("signUpModel") User signUp,
			BindingResult result, RedirectAttributes redirectAttributes) {

		signUpValidator.validate(signUp, result);
		if (result.hasErrors())
			redirectAttributes.addFlashAttribute("signUpModel", signUp).addFlashAttribute(
					BindingResult.MODEL_KEY_PREFIX + "signUpModel", result);
		else {
			userService.createUser(signUp);
			redirectAttributes.addFlashAttribute("signUpSuccess", "true");
		}
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ModelAndView processPassword(@Valid @ModelAttribute ForgotPasswordModel forgotPasswordModel,
			BindingResult result, RedirectAttributes redirectAttributes) {
		forgotPasswordValidator.validate(forgotPasswordModel, result);
		if (result.hasErrors())
			redirectAttributes.addFlashAttribute("forgotPasswordModel", forgotPasswordModel).addFlashAttribute(
					BindingResult.MODEL_KEY_PREFIX + "forgotPasswordModel", result);
		else {
			forgotPasswordService.start(forgotPasswordModel);
			redirectAttributes.addFlashAttribute("forgotPasswordSuccess", "true");
		}
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/activation*", method = RequestMethod.GET, params = { "id", "userName" })
	public ModelAndView activate(ModelMap model, @RequestParam(value = "id") String id,
			@RequestParam(value = "userName") String userName, RedirectAttributes redirectAttributes) {

		if (userService.activateUser(userName, id))
			redirectAttributes.addFlashAttribute("activationSuccess", "true");
		else
			redirectAttributes.addFlashAttribute("activationSuccess", "false");
		return new ModelAndView("redirect:/");
	}

}
