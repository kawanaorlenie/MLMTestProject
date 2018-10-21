package org.mlm.model.entity.Surveys;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.InheritanceType;
import javax.persistence.DiscriminatorType;

import org.mlm.model.entity.User;

@Entity
@Table(name = "surveys")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name="discriminator",
    discriminatorType=DiscriminatorType.STRING
)
public abstract class Survey {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne (fetch=FetchType.EAGER ,cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name="userId")
	private User respondent;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getRespondent() {
		return respondent;
	}

	public void setRespondent(User respondent) {
		this.respondent = respondent;
	}
}
