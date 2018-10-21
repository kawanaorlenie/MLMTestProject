package org.mlm.service.impl;

import java.util.List;

import org.mlm.dao.ChatDAO;
import org.mlm.dao.EventsDAO;
import org.mlm.dao.UserDAO;
import org.mlm.model.entity.ChatMessage;
import org.mlm.model.entity.Event;
import org.mlm.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatServiceImpl implements ChatService{

	@Autowired
	private EventsDAO eventsDAO;
	
	@Autowired
	private ChatDAO chatDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	
	@Transactional
	public List<ChatMessage> getEventChatMessages(int eventId) {
		Event event = eventsDAO.findOne(eventId);
		return event.getChatMessages();
	}


	@Transactional
	public boolean saveMessage(ChatMessage message) {
		/*Event e = message.getEvent();
		e.getChatMessages().add(message);
		eventsDAO.save(e);
		
		User u = message.getUser();
		u.getMessages().add(message);
		userDAO.save(u);*/
		chatDAO.save(message);
		return true;
	}

}
