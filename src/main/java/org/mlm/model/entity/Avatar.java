package org.mlm.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Avatar {

	private int avatarId;
	private byte[] image;

	@Lob
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] avatar) {
		this.image = avatar;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(int avatarId) {
		this.avatarId = avatarId;
	}

}
