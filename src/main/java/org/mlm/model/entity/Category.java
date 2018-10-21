package org.mlm.model.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "categories")
public class Category implements Serializable, Comparable<Category> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4231755407576179073L;
	
	@Id
	@Column(name = "category_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int categoryId;
	
	@Column(name = "category_name")
	private String categoryName;  //TODO: jakis wskaznik do resourcow albo co (multi language!)
	
	@Column(name = "category_icon_path")
	private String categoryIconPath;
	
	@JsonIgnore
	@ManyToMany(mappedBy="categories")
	private Set<User> users;

	@ManyToMany(mappedBy = "category", fetch=FetchType.LAZY ,cascade = { CascadeType.ALL })
	@OrderBy("registrationDate DESC")
	private Set<Event> events;
	
	@ManyToOne(fetch=FetchType.EAGER ,cascade = { CascadeType.ALL })
	private MasterCategory masterCategory;
	
	public Category(int categoryId, String categoryName,
			String categoryIconPath, MasterCategory masterCategory) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.categoryIconPath = categoryIconPath;
		this.masterCategory = masterCategory;
	}
	
	public Category()
	{}

	public MasterCategory getMasterCategory() {
		return masterCategory;
	}

	public void setMasterCategory(MasterCategory masterCategory) {
		this.masterCategory = masterCategory;
	}

	public Set<Event> getEvents() {
		return events;
	}

	public void setEvents(Set<Event> events) {
		this.events = events;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	
	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	
	public String getCategoryIconPath() {
		return categoryIconPath;
	}

	public void setCategoryIconPath(String categoryIconPath) {
		this.categoryIconPath = categoryIconPath;
	}

	public int compareTo(Category o) {
		return categoryId - o.categoryId;
	}

}