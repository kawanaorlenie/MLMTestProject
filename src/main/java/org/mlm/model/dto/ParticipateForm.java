package org.mlm.model.dto;

public class ParticipateForm {
	private int eventId;
	
	public ParticipateForm()
	{
		super();
	}
	
	public ParticipateForm(int l) {
		super();
		this.eventId = l;
	}
	
	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	
}
