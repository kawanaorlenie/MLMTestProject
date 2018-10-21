package org.mlm.controller;

import org.mlm.model.entity.Category;
import org.mlm.model.json.JTableJson;
import org.mlm.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/categories")
public class CategoryManagementController {

	@Autowired
	private CategoriesService categoriesService;

	@RequestMapping(value = "/list")
	@ResponseBody
	public JTableJson listCategories() {
		JTableJson jTableJson = new JTableJson("OK",
				categoriesService.getCategoryList(), null);
		return jTableJson;
	}

	@RequestMapping(value = "/create")
	@ResponseBody
	public JTableJson createCategories(@ModelAttribute Category categoryModel) {
		return new JTableJson("OK",
				categoriesService.createCategory(categoryModel), null);
	}

	@RequestMapping(value = "/update")
	@ResponseBody
	public JTableJson updateCategories(@ModelAttribute Category categoryModel) {
		return new JTableJson("OK",
				categoriesService.updateCategory(categoryModel), null);
	}

	@RequestMapping(value = "/delete")
	@ResponseBody
	public JTableJson deleteCategories(@ModelAttribute Category categoryModel) {
		categoriesService.deleteCategory(categoryModel);
		return new JTableJson("OK", null, null);
	}

}
