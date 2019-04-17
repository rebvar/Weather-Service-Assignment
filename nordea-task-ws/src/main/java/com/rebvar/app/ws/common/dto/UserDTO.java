package com.rebvar.app.ws.common.dto;

import java.io.Serializable;
import java.util.List;

import com.rebvar.app.ws.common.models.AbstractUserModel;


public class UserDTO extends AbstractUserModel implements Serializable {
	
	private String userId;
	private static final long serialVersionUID = 6154583116824995239L;
	private String password;
    private List<LocationWeatherDTO> favorites;
    private List<LocationWeatherDTO> weatherSearches;
    private String encryptedPassword;
    public List<LocationWeatherDTO> getWeatherSearches() {
		return weatherSearches;
	}
    
	public void setWeatherSearches(List<LocationWeatherDTO> weatherSearches) {
		this.weatherSearches = weatherSearches;
	}
	
	public List<LocationWeatherDTO> getFavorites() {
		return favorites;
	}
	
	public void setFavorites(List<LocationWeatherDTO> favorites) {
		this.favorites = favorites;
	}
    
    public String getUserId() {
		return userId;
	}
    
	public void setUserId(String userId) {
		this.userId = userId;
	}
    
    public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		
		this.encryptedPassword = encryptedPassword;
	}
	
	public String getEncryptedPassword() {
		
		return encryptedPassword;
	}
}
