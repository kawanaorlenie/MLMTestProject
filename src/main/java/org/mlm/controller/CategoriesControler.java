package org.mlm.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.mlm.model.dto.Categories_Form;
import org.mlm.model.entity.Category;
import org.mlm.model.entity.MasterCategory;
import org.mlm.model.entity.User;
import org.mlm.service.CategoriesService;
import org.mlm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

class CategoriesMAVBuilder extends ModelAndView {
	public CategoriesMAVBuilder addCategories(Iterable<Category> categories) {
		this.addObject(CategoriesService.ModelAttributes.categories, categories);
		return this;
	}

	public CategoriesMAVBuilder addCategoriesToShow(List<Category> categories) // TODO:
																				// remove
	{
		this.addObject(CategoriesService.ModelAttributes.categoriesToShow,
				categories);
		return this;
	}

	public CategoriesMAVBuilder addPageNr(int page) {
		this.addObject(CategoriesService.ModelAttributes.page, page);
		return this;
	}

	public CategoriesMAVBuilder addUser(User u) {
		this.addObject(CategoriesService.ModelAttributes.user, u);
		return this;
	}

	public CategoriesMAVBuilder addIsLastPage(boolean isLastPage) // TODO:
																	// remove
	{
		this.addObject(CategoriesService.ModelAttributes.isLastPage, isLastPage);
		return this;
	}

	public CategoriesMAVBuilder addEditSelectedCategoriesForm(
			String[] categoriesSelected) {
		Categories_Form catForm = new Categories_Form();
		catForm.setCategoriesSelected(categoriesSelected);
		this.addObject(CategoriesService.ModelAttributes.editCategoriesForm,
				catForm);
		return this;
	}

	public CategoriesMAVBuilder setSelectCategoryAsViewName() {
		this.setViewName(CategoriesService.ModelAttributes.view_selectCategory);
		return this;
	}

	public CategoriesMAVBuilder setEditSelectedCategoriesAsViewName() {
		this.setViewName(CategoriesService.ModelAttributes.view_editSelectedCategories);
		return this;
	}

	public CategoriesMAVBuilder addMasterCategories(Iterable<MasterCategory> masterCategories) {
		this.addObject(CategoriesService.ModelAttributes.masterCategories, masterCategories);
		return this;
	}

}

@Controller
public class CategoriesControler {

	@Autowired
	private CategoriesService categoriesService;
	
	@Autowired
	private UserService userService;

	private ModelAndView getCategoriesModelAndView(int page, ModelMap model,
			Principal principal) {
		CategoriesMAVBuilder MAV = new CategoriesMAVBuilder();
		User user = userService.getUserWithNotifications(principal.getName());
		List<Category> categories = categoriesService.getUserCategories(
				model, user.getUserId());
		boolean lastPage = categoriesService.isLastPage(page, categories);
		List<Category> categoriesToShow = categoriesService
				.getCategoriesToShow(page, categories);

		MAV.addCategories(categories).addCategoriesToShow(categoriesToShow)
				.addPageNr(page).addUser(user).addIsLastPage(lastPage)
				.setSelectCategoryAsViewName();

		return MAV;
	}

	@RequestMapping(value = "/user/categories/{page}", method = RequestMethod.GET)
	public ModelAndView showCategories(@PathVariable String page,
			ModelMap model, Principal principal) {
		return getCategoriesModelAndView(Integer.parseInt(page), model,
				principal);
	}

	@RequestMapping(value = "/user/categories", method = RequestMethod.GET)
	public ModelAndView showCategories(ModelMap model, Principal principal) {
		return getCategoriesModelAndView(1, model, principal);
	}

	@RequestMapping(value = "/user/categories/edit", method = RequestMethod.GET)
	public ModelAndView editCategories(ModelMap model, Principal principal) {

		CategoriesMAVBuilder MAV = new CategoriesMAVBuilder();
		
		Iterable<MasterCategory> masterCategories = categoriesService.getAllCategories();
		User user = userService.getUserWithNotifications(principal.getName());
		String[] categoriesSelected = categoriesService
				.getUserCategoriesIdlistByUserID(user.getUserId());

		MAV.setEditSelectedCategoriesAsViewName()
				.addEditSelectedCategoriesForm(categoriesSelected)
				.addMasterCategories(masterCategories).addUser(user);

		return MAV;
	}

	@RequestMapping(value = "/user/categories/edit", method = RequestMethod.POST)
	public ModelAndView processEditCategories(
			ModelMap model,
			@Valid @ModelAttribute("Categories_Form") Categories_Form selected_categories,
			BindingResult result, Principal principal) {

		User user = userService.getUserWithNotifications(principal.getName());
		categoriesService.saveUserCategories(user, selected_categories);
		return new ModelAndView("redirect:/user/categories/");
	}

}
