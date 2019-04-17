package com.rebvar.app.ws.weather_location_app.model.request;

import com.rebvar.app.ws.common.models.AbstractUserModel;

public class UserDataRequestModel extends AbstractUserModel {

	protected String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
