package com.rebvar.app.ws.weather_location_app.model.request;

/**
 * @author sehossei
 * JSON body structure for adding to/removing from favorites.
 */

public class UserFavoritesRequestModel {
	private String uniqueId;

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String id) {
		this.uniqueId = id;
	}	
}
