package org.mlm.dao;

import java.util.List;

import org.mlm.model.dto.SearchForm;
import org.mlm.model.entity.Event;

public interface SearchEventsDAO {
	/*public List<Event> selectEventsByFilter(int CategoryId, SearchForm keyword);*/
	public List<Event> selectEventsByFilter(SearchForm keyword);
}
