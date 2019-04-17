package com.rebvar.app.ws.db.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity(name="location_weather")
public class LocationWeatherEntity extends AbstractLocationWeatherEntity implements Serializable {

	private static final long serialVersionUID = 2829389183394955264L;
	
	@ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity userDetails;
	
	public UserEntity getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserEntity userDetails) {
		this.userDetails = userDetails;
	}
	
	
	//Is selected as favorite? There are three designs for such a circumstance.
	//1. Using a flag field - the current implementation.
	//2. Using an external table to store only the search ids, but it requres another stage of searching in the users 
	//   table to first find all the user searches and then select and filter those from the external table. 
	//   The relation there would be a 1 -> 0..1 relation from LocationWeather to the the foreign table.
	//3. Using an external table to hold the the user Id (One to Many from user) and the search Ids (One to Zero..One from the Search table)
	//   This design is probably the most efficient for storage, with shallow favorites compared to all searches
	//   but it has an obvious drawback of introducing a race condition in the cascading process of the relations. 
	//   Therefore, it is omitted.
	@Column(nullable = false)
	protected boolean isFavorite = false;
	
	public boolean isFavorite() {
		return isFavorite;
	}

	public void setFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
	}

	@OneToMany(mappedBy = "locationWeather", cascade=CascadeType.ALL)
	private List<ForecastItemEntity> forecasts;

	public List<ForecastItemEntity> getForecasts() {
		return forecasts;
	}

	public void setForecasts(List<ForecastItemEntity> forecasts) {
		this.forecasts = forecasts;
	}
	
	
}
