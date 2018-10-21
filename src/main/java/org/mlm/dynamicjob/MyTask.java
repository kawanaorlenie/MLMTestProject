package org.mlm.dynamicjob;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import org.mlm.model.entity.User;
import org.mlm.service.UserService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

public class MyTask extends QuartzJobBean {
	
	
	@Autowired
	UserService userService;
	
	public void performAction(Object [] args) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		
        System.out.println("Hey, you reached me...: hey hey");
        System.out.println((String)args[0] + (String)args[1]);
        
        User u = userService.findUser((String)args[0]);
        if(u!= null)
        	System.out.println(u.getEmail()+ " "+u.getUserName()+" "+u.getUserId());
        else System.out.println("user "+(String)args[0]+" = null");
        System.out.println("finished...:)");
        
    }

	/*@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
	
        System.out.println(dateFormat.format(date)+": you reached job.execute name= "+context.getJobDetail().getName());
        
        
	}*/

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
	
        System.out.println(dateFormat.format(date)+": you reached job.execute name= "+context.getJobDetail().getName());
        
	        UserService userService;
			try {
				userService = (UserService) context.getScheduler().getContext().get("aService");
				String username = (String) context.getJobDetail().getJobDataMap().get("username");
				User u = userService.findUser(username);
		        if(u!= null)
		        	System.out.println(u.getEmail()+ " "+u.getUserName()+" "+u.getUserId());
		        else System.out.println("user "+username+" = null");
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	   
	}  
	
	
}
