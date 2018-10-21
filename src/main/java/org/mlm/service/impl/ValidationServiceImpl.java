package org.mlm.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.mlm.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class ValidationServiceImpl implements ValidationService {

	@Autowired
	private MessageSource messageSource;

	public Map<String, String> createMassages(BindingResult result) {

		Map<String, String> messages = new HashMap<String, String>();
		for (FieldError e : result.getFieldErrors()) {
//			System.out.println(e.getField());
//			System.out.println(e.getCode());
//			System.out.println(e.getDefaultMessage());
//			System.out.println(e.getObjectName());
//			System.out.println(e.getCode() + "." + e.getObjectName() + "." + e.getField());
//			System.out.println(messageSource.getMessage(e.getCode() + "." + e.getObjectName() + "." + e.getField(),
//					null, null));
			//TODO : sprawdzic w pracy jak dostawac caly code
			messages.put(e.getField(), e.getDefaultMessage());
		}

		return messages;

	}

}
