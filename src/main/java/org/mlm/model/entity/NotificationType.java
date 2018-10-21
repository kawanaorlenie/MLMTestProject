package org.mlm.model.entity;

public enum NotificationType {
	
	
	NEW_PARTICIPANT (NotificationType._NEW_PARTICIPANT, "new participant in event"),
	YOUR_PARTICIPATION_CONFIRMED (NotificationType._YOUR_PARTICIPATION_CONFIRMED, "Your participation confirmed"),
	YOUR_PARTICIPATION_REJECTED (NotificationType._YOUR_PARTICIPATION_REJECTED, "Your participation rejected"),
	YOUVE_GOT_MAIL (NotificationType._YOUVE_GOT_MAIL, "you've got mail"),
	EVENT_CANCELED (NotificationType._EVENT_CANCELED, "event was canceled by organiser");
	
	public static final String _NEW_PARTICIPANT = "NEW_PARTICIPANT";
	public static final String _YOUR_PARTICIPATION_CONFIRMED = "YOUR_PARTICIPATION_CONFIRMED";
	public static final String _YOUR_PARTICIPATION_REJECTED = "YOUR_PARTICIPATION_REJECTED";
	public static final String _YOUVE_GOT_MAIL = "YOUVE_GOT_MAIL";
	public static final String _EVENT_CANCELED = "EVENT_CANCELED";
	
	private final String type;
	private final String message;
	
	
	private NotificationType(String type, String message) {
		this.type = type;
		
		this.message = message;
		
	}
	
	public String getMessage()
	{
		return this.message;
	}
	
	public String getType()
	{
		return this.type;
	}

	
}
