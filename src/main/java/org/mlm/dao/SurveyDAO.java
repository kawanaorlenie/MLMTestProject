package org.mlm.dao;

import org.mlm.model.entity.Surveys.Survey;
import org.springframework.data.repository.CrudRepository;

public interface SurveyDAO extends CrudRepository<Survey, Integer> {

}
