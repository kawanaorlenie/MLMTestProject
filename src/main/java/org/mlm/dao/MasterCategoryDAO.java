package org.mlm.dao;

import org.mlm.model.entity.MasterCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MasterCategoryDAO extends CrudRepository<MasterCategory, Integer>{

	@Query
	MasterCategory findByName(String name);

}
