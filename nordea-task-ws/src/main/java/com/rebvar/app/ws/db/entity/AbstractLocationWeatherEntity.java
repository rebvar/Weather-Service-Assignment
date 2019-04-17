package com.rebvar.app.ws.db.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.MappedSuperclass;

import javax.persistence.Id;


/**
 * @author sehossei
 * Super class for LocationWeather. The class is extended by anonymous and user location weather searches. The class itself extends 
 * WeatherDataEntity Class which is used for other classes including Forecast data.
 */
@MappedSuperclass
public abstract class AbstractLocationWeatherEntity extends WeatherDataEntity implements Serializable {

	private static final long serialVersionUID = 2829389183394955264L;
	
	@Id
	@GeneratedValue
	protected long id;
	
	@Column(nullable = true)
	protected String extra;
	
	@Column(nullable = false)
	protected double lat;
	
	@Column(nullable = false)
	protected double lon;
	
	@Column(nullable = false)
	protected boolean locationValid = false;
	
	@Column(nullable = false)
	protected String city;
	
	@Column(nullable = false)
	protected String countryCode;
	
	@Column(nullable = false)
	protected Date date;
	
	@Column(nullable = false)
	protected String uniqueId;
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUniqueId() {
		return uniqueId;
	}
	
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public boolean isLocationValid() {
		return locationValid;
	}

	public void setLocationValid(boolean locationValid) {
		this.locationValid = locationValid;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String raw) {
		this.extra = raw;
	}


	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
