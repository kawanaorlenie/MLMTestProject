package org.mlm.model;

import org.mlm.model.entity.Event;
import org.mlm.model.entity.EventParticipants;
import org.mlm.model.entity.User;

public class Event_div {
	//need to calculate some additional info for each event
	private Event e;
	private boolean registered;
	private boolean organizer;
	
	public Event_div(Event e, User user) {
		
		this.e = e;
		this.registered=false;
		
		for(EventParticipants participant: e.getParticipants())
		{
			
			if(participant.getUser().getUserName().equals(user.getUserName()))
				this.registered= true;
		}
		
		this.organizer = false;
		/*if(e.getUserName().equals(user.getUserName()))
			this.organizer = true;*/
		if(e.getOrganizer().getUserName().equals(user.getUserName()))
			this.organizer = true;
	}
	
	public boolean isRegistered() {
		return registered;
	}

	public void setRegistered(boolean registered) {
		this.registered = registered;
	}

	public Event getE() {
		return e;
	}
	public void setE(Event e) {
		this.e = e;
	}
	
	public boolean isOrganizer() {
		return organizer;
	}

	public void setOrganizer(boolean organizer) {
		this.organizer = organizer;
	}

	
}
