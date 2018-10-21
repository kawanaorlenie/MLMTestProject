package org.mlm.model.entity.Surveys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.mlm.model.entity.User;

@Entity
public class ParticipantPresence {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "USERID")
	private User participant;
	
	@Column(name = "was_present")
	private boolean wasParticipantPresent;
	
	@ManyToOne
	private OrganizerSurvey os;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getParticipant() {
		return participant;
	}
	public void setParticipant(User participant) {
		this.participant = participant;
	}
	public boolean isWasParticipantPresent() {
		return wasParticipantPresent;
	}
	public void setWasParticipantPresent(boolean wasParticipantPresent) {
		this.wasParticipantPresent = wasParticipantPresent;
	}
}
