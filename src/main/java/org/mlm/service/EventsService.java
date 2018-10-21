package org.mlm.service;

import java.util.List;

import org.mlm.model.dto.CancelEventForm;
import org.mlm.model.dto.ConfirmRejectParticipantForm;
import org.mlm.model.dto.ParticipateForm;
import org.mlm.model.dto.SearchForm;
import org.mlm.model.entity.Event;
import org.mlm.model.entity.User;
import org.mlm.model.entity.Surveys.OrganizerSurvey;
import org.mlm.model.entity.Surveys.Survey;

public interface EventsService {
	public static class ModelAttributes {
		public static final String view_show_events ="category_events";
		public static final String view_organizator_panel ="organizatorPanel";
		
		public static final String user = "user";
		public static final String eventForm = "EventForm";
		public static final String searchForm = "SearchForm";
		public static final String participateForm = "ParticipateForm";
		public static final String confirmForm = "ConfirmForm";
		public static final String categoryId = "categoryid";
		public static final String event = "event";
		public static final String events = "events";
		public static final String participations = "participations"; 
	}
	
	public List<Event> getEventListFromCategory(int categoryId);
	public List<Event> getEventListOrganizedByUser(String userName);
	public List<Event> searchCategoryEventsWithKeywords(int category, SearchForm keyword);
	public Event getEventById(int id);
	public Event getEventWitchChatById(int id);
	public void participateInEvent(User user, ParticipateForm participate);

	public void confirmParticipation(ConfirmRejectParticipantForm confirm);
	public boolean isUserEventOrganiser(User user, int eventId);
	public void registerEvent(Event event);
	public void rejectParticipation(ConfirmRejectParticipantForm reject);
	public List<Event> getEventListUserParticipatesIn(String userName);
	public void cancelEvent(CancelEventForm cancelForm);
	
	public void saveSurvey(Survey s);
	
}
