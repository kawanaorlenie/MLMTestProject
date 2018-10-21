package org.mlm.model.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;
import org.mlm.model.dto.EventForm;

@Entity
@Table(name = "events")
@Indexed 
@Analyzer (impl = org.apache.lucene.analysis.pl.PolishAnalyzer.class) 
public class Event implements Serializable, Comparable<Event>{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "event_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int eventId;

	
	@ManyToMany( fetch=FetchType.LAZY)
	@IndexedEmbedded
	private Set<Category> category;	
	
	@Column(name = "event_registration_date")
	private Date registrationDate;
	
	
	@Column(name = "event_date")
	private Date date;
	
	//TODO: group into Localization class?
	@Column(name = "city")
	@Field(index = Index.TOKENIZED,   store = Store.NO)
	private String City;
	@Column(name = "street")
	@Field(index = Index.TOKENIZED,   store = Store.NO)
	private String Street;
	@Column(name = "country")
	@Field(index = Index.TOKENIZED,   store = Store.NO)
	private String Country;

	@ManyToOne (fetch=FetchType.EAGER ,cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name="userId")
	@IndexedEmbedded
	private User organizer;
	
	@Column(name = "header")
	@Field(index = Index.TOKENIZED,   store = Store.YES)
	private String Header;
	
	@Column(name = "descryption")
	@Field(index = Index.TOKENIZED,   store = Store.YES)
	private String Descryption;
	
	@Column(name = "number_of_participants")
	@Field(index = Index.UN_TOKENIZED,   store = Store.NO)
	private int number_of_participants;
	
	@Column(name = "confirm_participants")
	@Field(index = Index.UN_TOKENIZED,   store = Store.NO)
	private Boolean confirmParticipants ;
	
	
	@OneToMany(fetch=FetchType.EAGER,  mappedBy = "event",  orphanRemoval = true)
	private List<EventParticipants> participants;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="event", cascade=CascadeType.ALL)
	@OrderBy("postDate DESC")
	private List<ChatMessage> chatMessages;

	public Event()
	{
		participants = new ArrayList<EventParticipants>();
		confirmParticipants = true;
		registrationDate = new Date();
	}
	
	public Event(EventForm event, Set<Category> categories, User org) {
		participants = new ArrayList<EventParticipants>();
		confirmParticipants = event.getConfirmParticipants();
		registrationDate = new Date();
		this.category = categories;
		this.City= event.getCity();
		this.Country = event.getCountry();
		
		
		try {
			System.out.println("date sent from js: "+event.getDate());
			this.date = new SimpleDateFormat("dd-MM-yyyy kk:mm").parse(event.getDate());
			System.out.println("date saved: "+date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.Descryption = event.getDescryption();
		this.eventId = event.getEventId();
		this.Header = event.getHeader();
		this.number_of_participants = event.getNumber_of_participants();
		this.Street = event.getStreet();
		/*this.UserName = event.getUserName();*/
		this.organizer = org;
		
	}

	public void addParticipant(EventParticipants e)
	{
		System.out.println("wywolano add participant!");
		participants.add(e);
		Collections.sort(participants);
	}
	
	public List<EventParticipants> getParticipants()
	{
		return participants;
	}
	
	public boolean getConfirmParticipants() {
		return confirmParticipants;
	}
	public void setConfirmParticipants(boolean confirm_participants) {
		this.confirmParticipants = confirm_participants;
	}
	public int getNumber_of_participants() {
		return number_of_participants;
	}
	public void setNumber_of_participants(int number_of_participants) {
		this.number_of_participants = number_of_participants;
	}
	
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
		this.eventId = eventId;
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
	
	
	
	public Date getDate() {
		return date;
	}
	public void setDate(String date) {
		Date dt = new Date();
		try {
			dt= new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(date);
			this.date = dt;
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public Set<Category> getCategory() {
		return category;
	}

	public void setCategory(Set<Category> category) {
		this.category = category;
	}
	
	public Date getRegistrationDate() {
		return registrationDate;
	}

	public List<ChatMessage> getChatMessages() {
		return chatMessages;
	}

	public void setChatMessages(List<ChatMessage> chatMessages) {
		this.chatMessages = chatMessages;
	}

	public User getOrganizer() {
		return organizer;
	}

	public void setOrganizer(User organizer) {
		this.organizer = organizer;
	}

	public int compareTo(Event o) {
		
		return date.compareTo(o.getDate());
	}
	
}
