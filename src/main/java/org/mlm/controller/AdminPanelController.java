package org.mlm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/adminPanel")
public class AdminPanelController {

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView showPanel() {
		return new ModelAndView("adminPanel");
	}

}
