package org.mlm.service;

import java.security.Principal;
import java.util.List;
import org.mlm.exceptions.UserNotFoundException;
import org.mlm.model.dto.Categories_Form;
import org.mlm.model.dto.EventForm;
import org.mlm.model.entity.Category;
import org.mlm.model.entity.MasterCategory;
import org.mlm.model.entity.User;
import org.springframework.ui.ModelMap;

public interface CategoriesService {

	public static class ModelAttributes {
		public static final String userName = "username";
		public static final String user = "user";
		public static final String categories = "categories";
		public static final String categoriesToShow = "categories_shown";
		public static final String page = "page";
		public static final String isLastPage = "lastpage";
		public static final String editCategoriesForm = "Categories_Form";
		public static final String masterCategories = "masterCategories";
		public static final String category = "category";

		public static final String view_selectCategory = "categories";
		public static final String view_editSelectedCategories = "select_categories";
		
	}

	public List<Category> getUserCategories(ModelMap model, int userId);

	public boolean isLastPage(int page, List<Category> categories);

	public List<Category> getCategoriesToShow(int page,
			List<Category> categories);

	public Categories_Form getSelectedCategories(ModelMap model,
			Principal principal) throws UserNotFoundException;

	public Iterable<Category> getCategoryList();

	public void saveUserCategories(User user,
			Categories_Form selected_categories);
	
	Category createCategory(Category categoryModel);
	Category updateCategory(Category categoryModel);
	void deleteCategory(Category categoryModel);
	
	public Category getCategoryById(int categoryId);
	public Category getCategoryByName(String categoryName);
	
	public String[] getUserCategoriesIdlistByUserID(int userId);
	public int registerEvent(EventForm event);

	public Iterable<MasterCategory> getAllCategories();

	public MasterCategory createMasterCategory(MasterCategory gryTowarzyskie);

	public MasterCategory getMastercategoryByName(String name);
}
