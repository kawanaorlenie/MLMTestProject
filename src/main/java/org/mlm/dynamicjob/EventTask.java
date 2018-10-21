package org.mlm.dynamicjob;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import org.mlm.model.entity.Event;
import org.mlm.model.entity.EventParticipants;
import org.mlm.model.entity.User;
import org.mlm.model.entity.Surveys.ParticipantSurvey;
import org.mlm.service.EventsService;
import org.mlm.service.UserService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

public class EventTask extends QuartzJobBean {
	
	public static final String eventServiceKey = "eventService";
	public static final String userServiceKey = "userService";
	public static final String usernameKey = "userName";
	public static final String eventIdKey = "eventId";
	
	@Autowired
	UserService userService;
	
	@Autowired
	EventsService eventsService;

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
	
        System.out.println(dateFormat.format(date)+": you reached job.execute name= "+context.getJobDetail().getName());
        
			try {
				userService = (UserService) context.getScheduler().getContext().get(EventTask.userServiceKey);
				eventsService = (EventsService) context.getScheduler().getContext().get(EventTask.eventServiceKey);
				String userName = (String) context.getJobDetail().getJobDataMap().get(EventTask.usernameKey);
				//TODO : linijka ponizej
				int eventId = 0;//(int) context.getJobDetail().getJobDataMap().get(EventTask.eventIdKey);
		        
				System.out.println("event organizer: "+userName);
				System.out.println("event id: "+eventId);
				
				Event e = eventsService.getEventById(eventId);
				
				//TODO: transactional save surveys
				for(EventParticipants ep : e.getParticipants())
				{
					ParticipantSurvey ps = new ParticipantSurvey();
					ps.setEventTookPlace(false);
					ps.setEverythinkWasOrganizedAsStated(false);
					ps.setRespondent(ep.getUser());
					ps.setId(0);
					
					eventsService.saveSurvey(ps);
					
					System.out.println("survey saved");
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}  
	
	
}
