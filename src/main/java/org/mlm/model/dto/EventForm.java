package org.mlm.model.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.mlm.model.entity.User;

public class EventForm {
	
	private int eventId;
	private String[] CategoriesSelected;
	private Date registrationDate;
	
	private User organizer;
	
	private String date;

	private String City;

	private String Street;

	private String Country;

	private String Header;

	private String Descryption;

	private int number_of_participants;

	private boolean confirmParticipants;
	
	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String[] getCategoriesSelected() {
		return CategoriesSelected;
	}

	public void setCategoriesSelected(String[] categoriesSelected) {
		CategoriesSelected = categoriesSelected;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getStreet() {
		return Street;
	}

	public void setStreet(String street) {
		Street = street;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public String getHeader() {
		return Header;
	}

	public void setHeader(String header) {
		Header = header;
	}

	public String getDescryption() {
		return Descryption;
	}

	public void setDescryption(String descryption) {
		Descryption = descryption;
	}

	public int getNumber_of_participants() {
		return number_of_participants;
	}

	public void setNumber_of_participants(int number_of_participants) {
		this.number_of_participants = number_of_participants;
	}

	public boolean getConfirmParticipants() {
		return confirmParticipants;
	}

	public void setConfirmParticipants(boolean confirmParticipants) {
		this.confirmParticipants = confirmParticipants;
	}

	/*public void setDate(String date) {
		Date dt = new Date();
		try {
			System.out.println("date sent from js: "+date);
			
			dt= new SimpleDateFormat("dd-MM-yyyy kk:mm").parse(date);
			System.out.println("date saved: "+dt);
			this.date = dt;
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}*/

	public User getOrganizer() {
		return organizer;
	}

	public void setOrganizer(User organizer) {
		this.organizer = organizer;
	}

	

}
