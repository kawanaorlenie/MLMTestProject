package org.mlm.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.mlm.dynamicjob.EventTask;
import org.mlm.dynamicjob.MyTask;
import org.mlm.model.dto.EventForm;
import org.mlm.model.entity.Event;
import org.mlm.model.entity.MasterCategory;
import org.mlm.model.entity.User;
import org.mlm.service.CategoriesService;
import org.mlm.service.EventsService;
import org.mlm.service.UserService;
import org.quartz.JobDataMap;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.JobDetailBean;
import org.springframework.scheduling.quartz.SimpleTriggerBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

class OrganizatorsPanelMAVBuilder extends ModelAndView {

	public OrganizatorsPanelMAVBuilder addEvents(List<Event> events_to_show) {
		this.addObject(EventsService.ModelAttributes.events, events_to_show);
		return this;
	}

	public OrganizatorsPanelMAVBuilder addCreateEventForm(User user) {
		EventForm e = new EventForm();
		e.setEventId(0);
		e.setOrganizer(user);
		this.addObject(EventsService.ModelAttributes.eventForm, e);
		return this;
	}

	public OrganizatorsPanelMAVBuilder setOrganizatorPanelAsViewName() {
		this.setViewName(EventsService.ModelAttributes.view_organizator_panel);
		return this;
	}

	public OrganizatorsPanelMAVBuilder addMasterCategories(
			Iterable<MasterCategory> masterCategories) {
		this.addObject(CategoriesService.ModelAttributes.masterCategories,
				masterCategories);
		return this;
	}
	
	public OrganizatorsPanelMAVBuilder addUser(
			User user) {
		this.addObject(CategoriesService.ModelAttributes.user,
				user);
		return this;
	}

	public OrganizatorsPanelMAVBuilder setParticipationsAsViewName() {
		this.setViewName(EventsService.ModelAttributes.participations);
		return this;
	}

}

@Controller
public class OrganizatorsPanelControler {

	@Autowired
	private EventsService eventsService;

	@Autowired
	private CategoriesService categoriesService;

	@Autowired
	private UserService userService;
	
	@Autowired 
	private ApplicationContext applicationContext;
	
	@RequestMapping(value = "/user/organizer", method = RequestMethod.GET)
	public ModelAndView showOrganizedEvents(ModelMap model, Principal principal) {
		OrganizatorsPanelMAVBuilder MAV = new OrganizatorsPanelMAVBuilder();

		User user = userService.getUserWithNotifications(principal.getName());
		List<Event> events_to_show = eventsService
				.getEventListOrganizedByUser(user.getUserName());
		Iterable<MasterCategory> masterCategories = categoriesService
				.getAllCategories();

		MAV.addCreateEventForm(user).setOrganizatorPanelAsViewName()
				.addEvents(events_to_show)
				.addMasterCategories(masterCategories)
				.addUser(user);

		return MAV;
	}
	
	//events in which user participates
	@RequestMapping(value = "/user/participations", method = RequestMethod.GET)
	public ModelAndView showUserEvents(ModelMap model, Principal principal) {
		OrganizatorsPanelMAVBuilder MAV = new OrganizatorsPanelMAVBuilder();
		User user = userService.getUserWithNotifications(principal.getName());
		
		List<Event> events_to_show = eventsService
				.getEventListUserParticipatesIn(user.getUserName());
		Iterable<MasterCategory> masterCategories = categoriesService
				.getAllCategories();

		MAV.addCreateEventForm(user).setParticipationsAsViewName()
				.addEvents(events_to_show)
				.addMasterCategories(masterCategories)
				.addUser(user);

		return  MAV;
	}
	
	@RequestMapping(value = "/user/organizer/createEvent", method = RequestMethod.POST)
	public ModelAndView processCreateForm(ModelMap model, @Valid @ModelAttribute("EventForm") EventForm event,
			BindingResult result, Principal principal) {
		// TODO: po stworzeniu eventa jak chce cos wyszukac to tworze nastepny
		// event - do poprawy
		ModelAndView MAV = new ModelAndView();
		User user = userService.findUser(principal.getName());
		event.setEventId(0);
		event.setOrganizer(user);
		
		System.out.println(event.getDate());
		
		int eventId = categoriesService.registerEvent(event);

		MAV = new ModelAndView("redirect:/user/organizer");
		
		event.setEventId(eventId);
		scheduleEventJob(event);
		
		return MAV;
	}

	private void scheduleEventJob(EventForm e) {
		Scheduler scheduler = (Scheduler) applicationContext.getBean("scheduler");
        EventTask eventTask = (EventTask) applicationContext.getBean("eventTask");
        
        JobDataMap jobDataMap = new JobDataMap();
        //loading only user name, eventId to save memory
        jobDataMap.put(EventTask.usernameKey, e.getOrganizer().getUserName());
        jobDataMap.put(EventTask.eventIdKey, e.getEventId());
        	
        JobDetailBean jobDetail = new JobDetailBean();
        jobDetail.setBeanName("MyJobDetail");
        jobDetail.setName("MyJobDetail");
        jobDetail.setJobClass(eventTask.getClass());
        jobDetail.setGroup(null);
        jobDetail.setJobDataMap(jobDataMap);
        jobDetail.afterPropertiesSet();
        
        SimpleTriggerBean trigger = new SimpleTriggerBean();
        trigger.setBeanName("MyTrigger");
        
        trigger.setJobDetail(jobDetail);
        try {
			trigger.setStartTime(new SimpleDateFormat("dd-MM-yyyy kk:mm").parse(e.getDate()));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        trigger.afterPropertiesSet();
        trigger.setRepeatCount(0);
        trigger.setRepeatInterval(0);
        
        try {
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException exc) {
			// TODO Auto-generated catch block
			exc.printStackTrace();
		}
	}
}
