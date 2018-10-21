package org.mlm.service;

import java.util.Map;

import org.springframework.validation.BindingResult;

public interface ValidationService {

	Map<String,String> createMassages(BindingResult result);

}
