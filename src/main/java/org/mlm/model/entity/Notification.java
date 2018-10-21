package org.mlm.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "notifications")
public class Notification {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	
	@ManyToOne (fetch=FetchType.EAGER, cascade={ CascadeType.MERGE})
	@JoinColumn(name="userId")
	private User user;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "link")
	private String link;
	
	public Notification()
	{
	}
	
	public Notification(NotificationType T, User u, String link)
	{
		this.type = T.getType();
		this.user = u;
		this.link = link;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public NotificationType getNotificationType()
	{
		if(type.equals(NotificationType._NEW_PARTICIPANT))
			return NotificationType.NEW_PARTICIPANT;
		else if(type.equals(NotificationType._YOUR_PARTICIPATION_CONFIRMED))
			return NotificationType.YOUR_PARTICIPATION_CONFIRMED;
		else if(type.equals(NotificationType._YOUVE_GOT_MAIL))
			return NotificationType.YOUVE_GOT_MAIL;
		else if(type.equals(NotificationType._YOUR_PARTICIPATION_REJECTED))
			return NotificationType.YOUR_PARTICIPATION_REJECTED;
		else if(type.equals(NotificationType._EVENT_CANCELED))
			return NotificationType.EVENT_CANCELED;
		else
			return null;
	}

}
