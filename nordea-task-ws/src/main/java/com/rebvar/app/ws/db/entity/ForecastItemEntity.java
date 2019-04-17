package com.rebvar.app.ws.db.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


/**
 * @author sehossei
 * Forecast table for user searches. 
 */
@Entity(name="location_weather_forecast")
public class ForecastItemEntity extends WeatherDataEntity implements Serializable {

	private static final long serialVersionUID = -4427058019610511511L;

	@Id
	@GeneratedValue
	protected long id;
	
	
	/**
	 * Link to the location weather search item
	 */
	@ManyToOne
    @JoinColumn(name="location_weather_id")
    private LocationWeatherEntity locationWeather;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocationWeatherEntity getLocationWeather() {
		return locationWeather;
	}

	public void setLocation_weather(LocationWeatherEntity locationWeather) {
		this.locationWeather = locationWeather;
	}
	
}
