package org.mlm.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "EVENT_PARTICIPANTS")

public class EventParticipants implements Serializable, Comparable<EventParticipants> {
	
	private static final long serialVersionUID = -770889403011527362L;

	
	//@EmbeddedId
	//private EventUserId eventUserId = new EventUserId();
	@Id
	@Column(name = "event_Participant_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "USERID")
	private User user;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "EVENT_ID")
	private Event event;
	
	@Column(name = "IS_CONFIRMED")
	private boolean isConfirmed;

	public EventParticipants()
	{
		
	}
	public EventParticipants(Event event, User user) {
		this.user = user;
		this.event=event;
	}

	public boolean isConfirmed() {
		return isConfirmed;
	}

	public void setConfirmed(boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}

	public User getUser() {
		return this.user;
	}
	
	public Event getEvent() {
		return this.event;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public int compareTo(EventParticipants o) {
		
		return this.getUser().getUserName().compareTo(o.getUser().getUserName());
	}
	
	public boolean equals(Object obj)
	{
		if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final EventParticipants other = (EventParticipants) obj;
        boolean sameEvent = this.event.getEventId() == other.event.getEventId();
        boolean sameUser = this.user.getUserId() == other.user.getUserId();
        return sameEvent && sameUser;
	}
}
