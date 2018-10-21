package org.mlm.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "ChatMessages")
public class ChatMessage {
	
	@Id
	@Column(name = "message_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long messageId;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="event_id")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Event event;
	
	@Column(name = "message")
	private String message;
	
	@Column(name = "post_date")
	private Date postDate;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private User user;
	
	public ChatMessage()
	{
		this.postDate = new Date();
	}
	
	public long getMessageId() {
		return messageId;
	}

	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getPostDate() {
		return postDate;
	}
	
}
