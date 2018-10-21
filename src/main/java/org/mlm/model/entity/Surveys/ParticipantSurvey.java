package org.mlm.model.entity.Surveys;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="surveys")
@DiscriminatorValue(value="P")
public class ParticipantSurvey extends Survey{
	
	@Column(name = "event_took_place")
	private boolean eventTookPlace;
	@Column(name = "everythink_ok")
	private boolean everythinkWasOrganizedAsStated;
	
	
	public boolean isEventTookPlace() {
		return eventTookPlace;
	}
	public void setEventTookPlace(boolean eventTookPlace) {
		this.eventTookPlace = eventTookPlace;
	}
	public boolean isEverythinkWasOrganizedAsStated() {
		return everythinkWasOrganizedAsStated;
	}
	public void setEverythinkWasOrganizedAsStated(
			boolean everythinkWasOrganizedAsStated) {
		this.everythinkWasOrganizedAsStated = everythinkWasOrganizedAsStated;
	}
}
