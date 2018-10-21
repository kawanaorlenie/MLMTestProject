package org.mlm.dao;

import java.util.List;

import org.mlm.model.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CategoryDAO extends CrudRepository<Category, Integer>
{
	@Query
	List<Category> findByCategoryIdIn(List<Integer> categoryIdList);
	
	@Query
	Category findByCategoryName(String categoryName);
	
}
