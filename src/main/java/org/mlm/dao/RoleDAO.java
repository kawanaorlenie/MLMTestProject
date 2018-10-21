package org.mlm.dao;

import org.mlm.model.entity.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleDAO extends CrudRepository<Role, Integer>{
	
	Role findByAuthority(String authority);
}
