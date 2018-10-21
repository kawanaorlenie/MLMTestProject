package org.mlm.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.mlm.dynamicjob.MyTask;
import org.mlm.model.Event_div;
import org.mlm.model.dto.CancelEventForm;
import org.mlm.model.dto.ConfirmRejectParticipantForm;
import org.mlm.model.dto.ParticipateForm;
import org.mlm.model.entity.ChatMessage;
import org.mlm.model.entity.Event;
import org.mlm.model.entity.User;
import org.mlm.service.CategoriesService;
import org.mlm.service.ChatService;
import org.mlm.service.EventsService;
import org.mlm.service.UserService;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.JobDetailBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

class EventMAVBuilder extends ModelAndView {
	public EventMAVBuilder addEventDiv(Event event, User user) {
		Event_div event_div = new Event_div(event, user);
		ParticipateForm pf = new ParticipateForm(event.getEventId());
		this.addObject( EventsService.ModelAttributes.participateForm + event.getEventId(), pf);
		for (int i = 0; i < event.getParticipants().size(); i++) {
			ConfirmRejectParticipantForm cf = new ConfirmRejectParticipantForm(
						i, event.getEventId());
			this.addObject(EventsService.ModelAttributes.confirmForm + event.getEventId() + "_" + i, cf);
		}
		
		this.addObject(ChatService.ModelAttributes.eventDiv, event_div);
		CancelEventForm cef = new CancelEventForm();
		cef.setEventId(event.getEventId());
		this.addObject(ChatService.ModelAttributes.cancelEventForm , cef);
		
		return this;
	}
	
	
	public EventMAVBuilder addChatMessages(List<ChatMessage> messages) {
		this.addObject(ChatService.ModelAttributes.chat_messages, messages);
		return this;
	}

	public EventMAVBuilder setShowChatAsViewName() {
		this.setViewName(ChatService.ModelAttributes.view_showChat);
		return this;
	}
	
	public EventMAVBuilder setNoSuchEventAsViewName() {
		this.setViewName(ChatService.ModelAttributes.view_noSuchEvent);
		return this;
	}
	
	public EventMAVBuilder addPostMessageForm(User user, Event event) {
		ChatMessage message = new ChatMessage();
		message.setUser(user);
		message.setEvent(event);
		message.setMessageId(0);
		this.addObject(ChatService.ModelAttributes.postMessageForm, message);
		return this;
	}
	
	public EventMAVBuilder addCategoryId(int categoryId) {
		this.addObject(EventsService.ModelAttributes.categoryId, categoryId);
		return this;
	}
	
	public EventMAVBuilder addEvent(Event event) {
		this.addObject(EventsService.ModelAttributes.event, event);
		return this;
	}
	
	public EventMAVBuilder addUser(User user) {
		this.addObject(CategoriesService.ModelAttributes.user, user);
		return this;
	}
}

@Controller
public class EventController {

	@Autowired
	private ChatService chatService;

	@Autowired
	private EventsService eventsService;

	@Autowired
	private UserService userService;
	
	private ModelAndView showMessages(ModelMap model, User user, Event event )
	{
		
		EventMAVBuilder MAV = new EventMAVBuilder();
		
		MAV.addChatMessages(event.getChatMessages())
				.setShowChatAsViewName()
				.addPostMessageForm(user, event)
				.addCategoryId(event.getCategory().iterator().next().getCategoryId())
				.addEventDiv(event, user)
				.addUser(user);
		return MAV;
	}
	
	private ModelAndView noSuchEvent(ModelMap model, User user) {
		EventMAVBuilder MAV = new EventMAVBuilder();
		MAV.setNoSuchEventAsViewName().addUser(user);
		return MAV;
	}
	
	@RequestMapping(value = "/user/event/{eventId}", method = RequestMethod.GET)
	public ModelAndView showChat(@PathVariable String eventId, ModelMap model,
			Principal principal) {
		User user = userService.getUserWithNotifications(principal.getName());
		Event event = eventsService.getEventWitchChatById(Integer.parseInt(eventId));
		userService.dropRelatedNotifications(user, "/user/event/"+eventId);
		
		if(event != null)
			return showMessages( model, user, event);
		else
			return noSuchEvent(model, user);
	}

	

	@RequestMapping(value = "/user/event/{eventId}/postMessage", method = RequestMethod.POST)
	public ModelAndView postMessage(@PathVariable String eventId,
			ModelMap model,
			@Valid @ModelAttribute("ChatMessage") ChatMessage message,
			BindingResult result, Principal principal) {

		User user = userService.getUserWithNotifications(principal.getName());
		Event event = eventsService.getEventById(Integer.parseInt(eventId));
		message.setUser(user);
		message.setEvent(event);
		message.setMessageId(0);
		chatService.saveMessage(message);
		ModelAndView MAV = new ModelAndView("redirect:/user/event/"+eventId);
		return MAV;
		
	}
	
	@RequestMapping(value = "/user/event/{eventId}/participate", method = RequestMethod.POST)
	//TODO: check if not > number of participants
	public ModelAndView processParticipate(
			@PathVariable String eventId,
			ModelMap model,
			@Valid @ModelAttribute("ParticipateForm") ParticipateForm participate,
			BindingResult result, Principal principal) {
		User user = userService.findUser(principal.getName());
		eventsService.participateInEvent(user, participate);

		ModelAndView MAV = new ModelAndView ("redirect:/user/event/"+eventId);//getEventsToShow(Integer.parseInt(category), model,principal);
		return MAV;
	}

	@RequestMapping(value = "/user/event/{eventId}/confirm", method = RequestMethod.POST)
	public ModelAndView processConfirm(
			@PathVariable String eventId,
			ModelMap model,
			@Valid @ModelAttribute("ConfirmParticipantForm") ConfirmRejectParticipantForm confirm,
			BindingResult result, Principal principal) {

		User user = userService.findUser(principal.getName());
		
		if (eventsService.isUserEventOrganiser(user, confirm.getEventId())) {

			eventsService.confirmParticipation(confirm);
		}

		ModelAndView MAV = new ModelAndView ("redirect:/user/event/"+eventId);//getEventsToShow(Integer.parseInt(category), model,principal);
		return MAV;
	}

	@RequestMapping(value = "/user/event/{eventId}/reject", method = RequestMethod.POST)
	public ModelAndView processReject(
			@PathVariable String eventId,
			ModelMap model,
			@Valid @ModelAttribute("ConfirmParticipantForm") ConfirmRejectParticipantForm reject,
			BindingResult result, Principal principal) {

		User user = userService.findUser(principal.getName());
		if (eventsService.isUserEventOrganiser(user, reject.getEventId())) {
			eventsService.rejectParticipation(reject);
		}

		ModelAndView MAV = new ModelAndView ("redirect:/user/event/"+eventId);
		return MAV;
	}
	
	@RequestMapping(value = "/user/event/{eventId}/remove", method = RequestMethod.POST)
	public ModelAndView processRemoveRegistered(
			@PathVariable String eventId,
			ModelMap model,
			@Valid @ModelAttribute("ConfirmParticipantForm") ConfirmRejectParticipantForm remove,
			BindingResult result, Principal principal) {

		User user = userService.findUser(principal.getName());
		if (eventsService.isUserEventOrganiser(user, remove.getEventId())) {
			eventsService.rejectParticipation(remove);
			userService.lowerReliability(user, UserService.ReliabilityLoweringType.REMOVE_PARTICIPANT);
		}

		ModelAndView MAV = new ModelAndView ("redirect:/user/event/"+eventId);//getEventsToShow(Integer.parseInt(category), model,principal);
		return MAV;
	}
	
	@RequestMapping(value = "/user/event/{eventId}/cancel", method = RequestMethod.POST)
	public ModelAndView processCancel(
			@PathVariable String eventId,
			ModelMap model,
			@Valid @ModelAttribute("CancelEventForm") CancelEventForm cancelForm,
			BindingResult result, Principal principal) {

		User user = userService.findUser(principal.getName());
		
		if (eventsService.isUserEventOrganiser(user, cancelForm.getEventId())) {

			eventsService.cancelEvent(cancelForm);
			userService.lowerReliability(user, UserService.ReliabilityLoweringType.CANCEL_EVENT);
		}

		ModelAndView MAV = new ModelAndView ("redirect:/user/event/"+eventId);//getEventsToShow(Integer.parseInt(category), model,principal);
		return MAV;
	}
	
	
	
}
