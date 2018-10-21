package org.mlm.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "activation")
public class ActivationModel {

	@Id
	private String userName;
	private String uuid;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public ActivationModel(String userName, String uuid) {
		this.userName = userName;
		this.uuid = uuid;
	}

	public ActivationModel() {
	}

}
