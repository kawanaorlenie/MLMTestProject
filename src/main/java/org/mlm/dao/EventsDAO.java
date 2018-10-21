package org.mlm.dao;

import org.mlm.model.entity.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventsDAO extends CrudRepository<Event, Integer>{/*
	@Query
	public List<Event> findByCategoryId(Integer CategoryId);*/
}
