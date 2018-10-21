package org.mlm.dao;

import org.mlm.model.entity.ChatMessage;
import org.springframework.data.repository.CrudRepository;

public interface ChatDAO extends CrudRepository<ChatMessage, Integer>{

}
