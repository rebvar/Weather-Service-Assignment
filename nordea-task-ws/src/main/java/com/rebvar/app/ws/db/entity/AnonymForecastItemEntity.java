package com.rebvar.app.ws.db.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


/**
 * @author sehossei
 * Forecast Data Stored for anonymous searches. Similar to ForecastDataItemEntity, but stored separately to 
 * ensure the integrity of relationships between the tables.
 * 
 */
@Entity(name="anonym_location_weather_forecast")
public class AnonymForecastItemEntity extends WeatherDataEntity implements Serializable {

	private static final long serialVersionUID = -4427058019610511511L;

	@Id
	@GeneratedValue
	protected long id;
	
	//Each weather search can have many predictions.
	@ManyToOne
    @JoinColumn(name="anonym_location_weather_id")
    private AnonymLocationWeatherEntity anonymLocationWeather;

	public AnonymLocationWeatherEntity getAnonymLocationWeather() {
		return anonymLocationWeather;
	}

	public void setAnonymLocationWeather(AnonymLocationWeatherEntity anonymLocationWeather) {
		this.anonymLocationWeather = anonymLocationWeather;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
