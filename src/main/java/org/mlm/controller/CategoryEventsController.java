package org.mlm.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.mlm.model.dto.SearchForm;
import org.mlm.model.entity.Category;
import org.mlm.model.entity.Event;
import org.mlm.model.entity.MasterCategory;
import org.mlm.model.entity.User;
import org.mlm.service.CategoriesService;
import org.mlm.service.EventsService;
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

//TODO: wyrzucanie partycypanta z eventa, rezygnacja <- spadek wiarygodnoœci?
//TODO: sprawdzenie categoryId <- NumberFormatException (np. czy nie jest puste albo literki)
//TODO: zoptymalizowanie (jak wyglada funkcja optymalizacji?) userow itp, zeby tego nie wyciagal z bazy tylko najpierw sprawdzil czy juz czasem tego nie ma
class Category_eventsMAVBuilder extends ModelAndView {
	public Category_eventsMAVBuilder addEvents(List<Event> events_to_show, User user) {
		this.addObject(EventsService.ModelAttributes.events,
				events_to_show);
		return this;
	}

	public Category_eventsMAVBuilder addSearchForm(User user, Category category) {
		this.addObject(EventsService.ModelAttributes.searchForm,
				new SearchForm());
		return this;
	}

	public Category_eventsMAVBuilder addCategoryId(int categoryId) {
		this.addObject(EventsService.ModelAttributes.categoryId, categoryId);
		return this;
	}
	
	public Category_eventsMAVBuilder addUser(User user) {
		this.addObject(EventsService.ModelAttributes.user, user);
		return this;
	}
	
	public Category_eventsMAVBuilder addMasterCategories(Iterable<MasterCategory> masterCategories) {
		this.addObject(CategoriesService.ModelAttributes.masterCategories, masterCategories);
		return this;
	}

	public Category_eventsMAVBuilder setShowEventsAsViewName() {
		this.setViewName(EventsService.ModelAttributes.view_show_events);
		return this;
	}

	public Category_eventsMAVBuilder addCategory(Category category) {
		this.addObject(CategoriesService.ModelAttributes.category, category);
		return this;
	}

}

@Controller
public class CategoryEventsController {

	@Autowired
	private EventsService eventsService;

	@Autowired
	private CategoriesService categoriesService;
	
	@Autowired
	private UserService userService;

	private ModelAndView getEventsToShow(int categoryId, ModelMap model,
			Principal principal) {
		Category_eventsMAVBuilder MAV = new Category_eventsMAVBuilder();
		User user = userService.getUserWithNotifications(principal.getName());
		Category category = categoriesService
				.getCategoryById(categoryId);
		List<Event> events_to_show = eventsService
				.getEventListFromCategory(categoryId);

		Iterable<MasterCategory> masterCategories = categoriesService.getAllCategories();
		
		MAV.addEvents(events_to_show, user)
				.addSearchForm(user, category).addCategory(category).addUser(user)
				.addMasterCategories(masterCategories)
				.setShowEventsAsViewName().addAllObjects(model);

		return  MAV;
	}

	@RequestMapping(value = "/user/categories/{category}/events", method = RequestMethod.GET)
	public ModelAndView showEvents(@PathVariable String category,
			ModelMap model, Principal principal) {
		return getEventsToShow(Integer.parseInt(category), model, principal);
	}
	
	@RequestMapping(value = "/user/categories/{category}/events/search", method = RequestMethod.POST)
	public ModelAndView processSearch(@PathVariable String category,
			ModelMap model,
			@Valid @ModelAttribute("SearchForm") SearchForm keyword,
			BindingResult result, Principal principal) {

		Category_eventsMAVBuilder MAV = new Category_eventsMAVBuilder();
		User user = userService.getUserWithNotifications(principal.getName());
		Category categoryModel = categoriesService.getCategoryById(Integer
				.parseInt(category));
		List<Event> events_to_show = eventsService
				.searchCategoryEventsWithKeywords(categoryModel.getCategoryId(),
						keyword);

		MAV.addEvents(events_to_show, user).addCategoryId(categoryModel.getCategoryId()).addUser(user)
				.setShowEventsAsViewName().addAllObjects(model);

		return MAV;
	}


	
}
