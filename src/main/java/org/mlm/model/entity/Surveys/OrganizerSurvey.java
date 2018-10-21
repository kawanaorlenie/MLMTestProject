package org.mlm.model.entity.Surveys;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@DiscriminatorValue(value="O")
@Table(name="surveys")
public class OrganizerSurvey extends Survey{
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="os", cascade=CascadeType.ALL)
	private Set<ParticipantPresence> participantsPresence;
	
	public Set<ParticipantPresence> getParticipantsPresence() {
		return participantsPresence;
	}

	public void setParticipantsPresence(
			Set<ParticipantPresence> participantsPresence) {
		this.participantsPresence = participantsPresence;
	}
}
