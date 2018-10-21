package org.mlm.model.dto;

public class ConfirmRejectParticipantForm {
	
	private int eventId;
	private int participantNumberOnTheList;
	
	public  ConfirmRejectParticipantForm()
	{
	}
	
	public  ConfirmRejectParticipantForm(int participantNumberOnTheList, int eventId) {
		this.participantNumberOnTheList=participantNumberOnTheList;
		this.setEventId(eventId);
	}

	public int getParticipantNumberOnTheList() {
		return participantNumberOnTheList;
	}

	public void setParticipantNumberOnTheList(int participantNumberOnTheList) {
		this.participantNumberOnTheList = participantNumberOnTheList;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	
	
}
