package com.rebvar.app.ws.db.entity;

import java.io.Serializable;

import javax.persistence.Column;

import javax.persistence.MappedSuperclass;

import com.rebvar.app.ws.common.models.AbstractUserModel;

@MappedSuperclass
public class AbstractUserEntity extends AbstractUserModel implements Serializable {
	
	private static final long serialVersionUID = 8225342489296017278L;
	
	@Column(nullable = false, unique = true)
	private String userId;
	
	@Column(nullable = false)
	private String encryptedPassword;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
