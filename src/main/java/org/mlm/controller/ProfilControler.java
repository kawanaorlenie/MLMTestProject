package org.mlm.controller;

import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.mlm.model.dto.ChangePasswordModel;
import org.mlm.model.entity.Profile;
import org.mlm.model.json.GeneralJson;
import org.mlm.service.UserService;
import org.mlm.service.ValidationService;
import org.mlm.validator.ChangePasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller providing process of user Profile Model
 * 
 * @author matrom
 */
@Controller
public class ProfilControler {

	@Autowired
	private ChangePasswordValidator changePasswordValidator;
	@Autowired
	private UserService userService;
	@Autowired
	private ValidationService validationService;

	@RequestMapping(value = "user/profile/changePassword", method = RequestMethod.POST)
	@ResponseBody
	public GeneralJson processForm(@Valid ChangePasswordModel changePasswordModel, BindingResult result,
			Principal principal) {

		changePasswordValidator.validate(principal.getName(), changePasswordModel, result);
		if (result.hasErrors()) {

			return new GeneralJson("ERROR", validationService.createMassages(result), "Validation error");

		} else {
			userService.changePassword(principal.getName(), changePasswordModel);
			return new GeneralJson("OK", null, "Password has been changed");
		}
	}

	@RequestMapping(value = "/user/profile", method = RequestMethod.GET)
	public ModelAndView showProfile(Principal principal) {
		Profile userProfile = userService.getProfile(principal.getName());
		return new ModelAndView("profile", "userProfile", userProfile).addObject("changePasswordModel",
				new ChangePasswordModel());
	}
	
	@RequestMapping(value = "/user/profile/{userId}", method = RequestMethod.GET)
	public ModelAndView showProfileByUserId(@PathVariable String userId, ModelMap model,
			Principal principal) {
		int id = Integer.parseInt(userId);
		Profile userProfile = userService.getProfile(id);
		if (userProfile != null)
		{
			ModelAndView MAV = new ModelAndView("UserProfile");
			MAV.addObject("userProfile", userProfile);
			return MAV;
		}
		else
			return new ModelAndView("noSuchUser");
	}

	@RequestMapping(value = "/user/profile/update")
	@ResponseBody
	public GeneralJson updateProfile(@Valid Profile userProfile, BindingResult result, Principal principal) {
		userService.saveUserProfile(principal.getName(), userProfile);
		return new GeneralJson("OK", userProfile, "Profile has been updated");
	}

	@RequestMapping(value = "/user/profile/update/photo", method = RequestMethod.POST)
	public ModelAndView uloadPhoto(@ModelAttribute("file") MultipartFile file, Principal principal) throws IOException {
		userService.saveUserAvatar(principal.getName(), file);
		return new ModelAndView("redirect:/user/profile");
	}

	@RequestMapping("/avatar")
	public void getImage(HttpServletResponse response, Principal principal) throws IOException, SQLException {
		byte[] avatar = userService.getAvatar(principal.getName());
		if (avatar != null) {
			response.setContentType("image/jpeg");
			response.getOutputStream().write(avatar);
			response.getOutputStream().flush();
		}
	}

}
