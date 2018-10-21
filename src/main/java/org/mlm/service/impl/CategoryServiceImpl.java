package org.mlm.service.impl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.mlm.dao.CategoryDAO;
import org.mlm.dao.EventsDAO;
import org.mlm.dao.MasterCategoryDAO;
import org.mlm.dao.UserDAO;
import org.mlm.exceptions.UserNotFoundException;
import org.mlm.model.dto.Categories_Form;
import org.mlm.model.dto.EventForm;
import org.mlm.model.entity.Category;
import org.mlm.model.entity.Event;
import org.mlm.model.entity.MasterCategory;
import org.mlm.model.entity.User;
import org.mlm.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

@Service
@Transactional
public class CategoryServiceImpl implements CategoriesService {

	private static final int MAX_NUMBER_OF_CATEGORIES_FOR_PAGE = 6;

	@Autowired
	private CategoryDAO categoryDAO;

	@Autowired
	private EventsDAO eventsDAO;

	
	@Autowired
	private MasterCategoryDAO masterCategoryDAO;
	
	@Autowired
	private UserDAO userDAO;

	public void saveUserCategories(User user, Categories_Form selected_categories) {
		// TODO: do poprawy - kazde zapytanie osobno ¿al

		user.setCategories(new HashSet<Category>());
		for (String categoryId : selected_categories.getCategoriesSelected())
			user.getCategories().add(categoryDAO.findOne(Integer.parseInt(categoryId)));
		userDAO.save(user);
	}

	@Transactional
	public String[] getUserCategoriesIdlistByUserID(int userId) {
		User user = userDAO.findOne(userId);
		String[] result = new String[user.getCategories().size()];
		int i = 0;
		for (Category userCategory : user.getCategories())
			result[i++] = Integer.toString(userCategory.getCategoryId());
		return result;
	}

	@Transactional
	private List<Category> getUserCategoriesByUserID(int userId) {
		User user = userDAO.findOne(userId);
		Hibernate.initialize(user.getCategories());
		ArrayList<Category> userCategories= new ArrayList<Category>(user.getCategories());
		Collections.sort(userCategories);
		return userCategories;
	}


	@SuppressWarnings("unchecked")
	public List<Category> getUserCategories(ModelMap model, int userId) {
		List<Category> categories = new ArrayList<Category>(MAX_NUMBER_OF_CATEGORIES_FOR_PAGE);
		if (model.containsAttribute(ModelAttributes.categories)) {
			categories = (ArrayList<Category>) model.get(ModelAttributes.categories);
		} else {
			categories = this.getUserCategoriesByUserID(userId);
		}
		return categories;
	}

	public boolean isLastPage(int page, List<Category> categories) {
		if (page > (((int) categories.size() - 1) / MAX_NUMBER_OF_CATEGORIES_FOR_PAGE) + 1) {
			page = ((int) categories.size() - 1) / MAX_NUMBER_OF_CATEGORIES_FOR_PAGE + 1;
		}

		if ((((int) categories.size() - 1) / MAX_NUMBER_OF_CATEGORIES_FOR_PAGE) + 1 == page) {
			return (true);
		} else {
			return (false);
		}
	}

	//TODO: move to js
	public List<Category> getCategoriesToShow(int page, List<Category> categories) {
		ArrayList<Category> categories_shown = new ArrayList<Category>(6);
		for (int i = (page - 1) * MAX_NUMBER_OF_CATEGORIES_FOR_PAGE; i < (page) * MAX_NUMBER_OF_CATEGORIES_FOR_PAGE; i++) {
			if (i < categories.size()) {
				categories_shown.add(categories.get(i));
			} else {
				break;
			}
		}
		return categories_shown;
	}

	public Categories_Form getSelectedCategories(ModelMap model, Principal principal) throws UserNotFoundException {
		User user = userDAO.findByUserName(principal.getName());

		Categories_Form selected_categories = new Categories_Form();

		String[] a = getUserCategoriesIdlistByUserID(user.getUserId());
		selected_categories.setCategoriesSelected(a);

		return selected_categories;
	}

	public Iterable<Category> getCategoryList() {

		return categoryDAO.findAll();
	}

	public Category createCategory(Category categoryModel) {
		
		MasterCategory mc = masterCategoryDAO.findOne(categoryModel.getMasterCategory().getId());
		mc.getCategories().add(categoryModel);
		masterCategoryDAO.save(mc);
		return categoryModel;
	}

	public Category updateCategory(Category categoryModel) {
		return categoryDAO.save(categoryModel);
	}

	public void deleteCategory(Category categoryModel) {
		categoryDAO.delete(categoryModel);
	}

	@Transactional
	public Category getCategoryById(int categoryId) {
		
		Category cm = categoryDAO.findOne(categoryId);
		cm.getEvents();
		return cm;
	}
	
	

	@Transactional
	public int registerEvent( EventForm event) {
		User org = userDAO.findByUserName(event.getOrganizer().getUserName());
		
		List<Integer> categoryIdList = new ArrayList<Integer>();
		for(String category: event.getCategoriesSelected())
		{
			categoryIdList.add(Integer.parseInt(category));
		}
		Set<Category> categories = new HashSet<Category>(categoryDAO.findByCategoryIdIn(categoryIdList));
		Event e = new Event(event, categories, org);
		/*if(org != null)
			org.getOrganized_events().add(e);
		else System.out.println("null user");*/
		e = eventsDAO.save(e);
		return e.getEventId();
		
	}

	public Category getCategoryByName(String categoryName) {
		
		return categoryDAO.findByCategoryName(categoryName);
	}

	@Transactional
	public Iterable<MasterCategory> getAllCategories() {
		Iterable<MasterCategory> result = masterCategoryDAO.findAll();
		for(MasterCategory mc: result)
			Hibernate.initialize(mc.getCategories());
		return result;
	}

	public MasterCategory createMasterCategory(MasterCategory gryTowarzyskie) {
		return masterCategoryDAO.save(gryTowarzyskie);
	}

	public MasterCategory getMastercategoryByName(String name) {
		
		return masterCategoryDAO.findByName(name);
	}
}
