package org.mlm.service;

import java.util.List;

import org.mlm.model.entity.ChatMessage;

public interface ChatService {
	public static class ModelAttributes {
		public static final String chat_messages = "chat_messages";
		
		public static final String view_showChat = "event";

		public static final String postMessageForm = "ChatMessage";

		public static final String eventDiv = "EventDiv";

		public static final String view_noSuchEvent = "noSuchEvent";

		public static final String cancelEventForm = "CancelEventForm";
	}
	
	public List<ChatMessage> getEventChatMessages(int eventId);
	public boolean saveMessage(ChatMessage message);
	
}
