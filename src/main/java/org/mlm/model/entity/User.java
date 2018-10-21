package org.mlm.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.mlm.model.entity.Surveys.Survey;

@Entity
@Table(name = "user")
public class User implements Serializable {

	private static final long serialVersionUID = -1659965219701237886L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;
	
	@Size(min = 6, max = 20)
	@Column(unique = true)
	@Field(index = Index.UN_TOKENIZED,   store = Store.NO)
	private String userName;
	
	@NotEmpty
	private String password;
	
	@NotEmpty
	@Email
	@Column(unique = true)
	private String email;
	
	private boolean enabled;
	
	@Transient
	private String confirmPassword;
	
	@Column(name = "reliability")
	private double reliability;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private Collection<Role> roles = new ArrayList<Role>();
	
	@OneToOne(cascade = CascadeType.ALL)
	private Profile profile;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Category> categories;

	@OneToMany(mappedBy="organizer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Event> organized_events = new HashSet<Event>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private List<EventParticipants> events;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Notification> notifications;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "respondent", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Survey> surveys;

	@OneToMany(fetch=FetchType.LAZY ,mappedBy="user" ,cascade = { CascadeType.ALL })
	private List<ChatMessage> messages;
	
	public User()
	{
		notifications = new HashSet<Notification>();
		reliability = 0.5;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	
	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public List<ChatMessage> getMessages() {
		return messages;
	}
	
	public List<EventParticipants> getEvents() {
		return events;
	}

	public Set<Event> getOrganized_events() {
		return organized_events;
	}

	public void setOrganized_events(Set<Event> organized_events) {
		this.organized_events = organized_events;
	}

	public Set<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(Set<Notification> notifications) {
		this.notifications = notifications;
	}

	public double getReliability() {
		return reliability;
	}

	public void setReliability(double reliability) {
		this.reliability = reliability;
	}

	public Set<Survey> getSurveys() {
		return surveys;
	}

	public void setSurveys(Set<Survey> surveys) {
		this.surveys = surveys;
	}
}