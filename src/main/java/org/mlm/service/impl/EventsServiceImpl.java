package org.mlm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.mlm.dao.CategoryDAO;
import org.mlm.dao.EventParticipantsDAO;
import org.mlm.dao.EventsDAO;
import org.mlm.dao.NotificationDAO;
import org.mlm.dao.SearchEventsDAO;
import org.mlm.dao.SurveyDAO;
import org.mlm.dao.UserDAO;
import org.mlm.model.dto.CancelEventForm;
import org.mlm.model.dto.ConfirmRejectParticipantForm;
import org.mlm.model.dto.ParticipateForm;
import org.mlm.model.dto.SearchForm;
import org.mlm.model.entity.Category;
import org.mlm.model.entity.Event;
import org.mlm.model.entity.EventParticipants;
import org.mlm.model.entity.Notification;
import org.mlm.model.entity.NotificationType;
import org.mlm.model.entity.User;
import org.mlm.model.entity.Surveys.OrganizerSurvey;
import org.mlm.model.entity.Surveys.Survey;
import org.mlm.service.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventsServiceImpl implements EventsService {

	@Autowired
	EventsDAO eventsDAO;
	
	@Autowired
	SurveyDAO surveyDAO;

	@Autowired
	CategoryDAO catDAO;
	
	@Autowired
	UserDAO userDAO;

	@Autowired
	SearchEventsDAO searchEventsDAO;
	
	@Autowired
	EventParticipantsDAO eventParticipantsDAO;
	
	@Autowired
	NotificationDAO notificationDAO;

	@Transactional
	public List<Event> getEventListFromCategory(int categoryId) {
		
		Category cm = catDAO.findOne(categoryId);
		return new ArrayList<Event>(cm.getEvents());
	}
	

	@Transactional
	public List<Event> searchCategoryEventsWithKeywords(int category,
			SearchForm keyword) {
		/*return searchEventsDAO.selectEventsByFilter(category, keyword);*/
		return searchEventsDAO.selectEventsByFilter( keyword);
	}

	@Transactional
	public void participateInEvent(User user, ParticipateForm participate) {

		Event e = eventsDAO.findOne(participate.getEventId());
		User u = userDAO.findOne(user.getUserId());
		EventParticipants epa = new EventParticipants(
				e, u);
		epa.setConfirmed(!e.getConfirmParticipants());
		e.addParticipant(epa);
		u.getEvents().add(epa);
		
		Notification n = new Notification(NotificationType.NEW_PARTICIPANT, e.getOrganizer(), "/user/event/"+e.getEventId());
		
		notificationDAO.save(n); //remove in EventController /user/event/{eventId}
		
		eventsDAO.save(e);
		eventParticipantsDAO.save(epa);
		userDAO.save(u);
	}

	@Transactional
	public void confirmParticipation(ConfirmRejectParticipantForm confirm) {
		Event e = eventsDAO.findOne(confirm.getEventId());
		EventParticipants epa = e.getParticipants().get(confirm.getParticipantNumberOnTheList());
		epa.setConfirmed(true);
		eventParticipantsDAO.save(epa);
		
		Notification n = new Notification(NotificationType.YOUR_PARTICIPATION_CONFIRMED, epa.getUser(), "/user/event/"+e.getEventId());
		notificationDAO.save(n); //remove in EventController /user/event/{eventId}
		
	}

	@Transactional
	public boolean isUserEventOrganiser(User user, int eventId) {
		Event e = eventsDAO.findOne(eventId);
		
		
		if((e!= null)&&(user!=null))
		{
			String organizer = e.getOrganizer().getUserName();
			if(organizer != null)
			{
				
				return organizer.equals(user.getUserName());
			}
			else 
			{
				System.out.println("null organizer");
				return false;
			}
		}
		else 
		{
			System.out.println("null event or user");
			return false;
		}
	}

	@Transactional
	public void registerEvent(Event event) {
		eventsDAO.save(event);
	}

	@Transactional
	public void rejectParticipation(ConfirmRejectParticipantForm reject) {
		Event e = eventsDAO.findOne(reject.getEventId());
		EventParticipants epart = e.getParticipants().get(reject.getParticipantNumberOnTheList());
		eventParticipantsDAO.delete(epart);
		
		User u = epart.getUser();
		u.getEvents().remove(epart);
		userDAO.save(u);
		
		e.getParticipants().remove(epart);
		eventsDAO.save(e);
		
		Notification n = new Notification(NotificationType.YOUR_PARTICIPATION_REJECTED, epart.getUser(), "/user/event/"+e.getEventId());
		notificationDAO.save(n); //remove in EventController /user/event/{eventId}
		
		
	}

	@Transactional
	public Event getEventById(int id) {
		
		return eventsDAO.findOne(id);
	}
	
	@Transactional
	public Event getEventWitchChatById(int id) {
		Event res = eventsDAO.findOne(id);
		if(res != null)
		{
			Hibernate.initialize(res.getChatMessages());
			Hibernate.initialize(res.getCategory());
		}
		return res;
	}


	@Transactional
	public List<Event> getEventListOrganizedByUser(String userName) {
		User organizer = userDAO.findByUserName(userName);
		Hibernate.initialize(organizer.getOrganized_events());
		for (Event e: organizer.getOrganized_events())
		{
			Hibernate.initialize(e.getCategory());
		}
		return new ArrayList<Event>(organizer.getOrganized_events());
	}


	@Transactional
	public List<Event> getEventListUserParticipatesIn(String userName) {
		User participant = userDAO.findByUserName(userName);
		List<EventParticipants> ep = participant.getEvents();
		List<Event> result  = new ArrayList<Event>();
		for(EventParticipants e : ep)
		{
			Event event = e.getEvent();
			result.add(event);
			Hibernate.initialize(event.getCategory());
		}
		return result;
	}


	@Transactional
	public void cancelEvent(CancelEventForm cancelForm) {
		Event e = eventsDAO.findOne(cancelForm.getEventId());
		List<EventParticipants> ep = e.getParticipants();
		for(EventParticipants e_part : ep)
		{
			Notification n = new Notification(NotificationType.EVENT_CANCELED, e_part.getUser(), "/user/event/"+e.getEventId());
			notificationDAO.save(n); //remove in EventController /user/event/{eventId}
		}
		//TODO: instead of delete - move to archive or sth like this
		eventsDAO.delete(cancelForm.getEventId());
				
	}


	@Transactional
	public void saveSurvey(Survey s) {
		surveyDAO.save(s);
	}

}
