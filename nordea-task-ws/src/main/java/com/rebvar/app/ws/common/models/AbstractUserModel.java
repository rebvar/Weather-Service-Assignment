package com.rebvar.app.ws.common.models;

public abstract class AbstractUserModel {
	
	//Common fields for the user field.
    protected String name;
    protected String email;
    
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
