package com.rebvar.app.ws.common.models;

import java.util.Date;
import java.util.List;
import java.util.Set;

public abstract class AbstractLocationWeatherModel extends WeatherData {
	
	//location related fields.
	
	protected double lat;
	protected double lon;
	protected boolean locationValid = false;
	protected String city;
	protected String countryCode;
	
	//for future. We can also store, raw unprocessed external data, for extra processing
	//in later stages
	protected String extra;
	
	protected Date date;
	
	//public search Id
	protected String uniqueId;
	
	
	//Is selected as favorite? There are three designs for such a circumstance. 
	//1. Using a flag field - the current implementation.
	//2. Using an external table to store only the search ids, but it requres another stage of searching in the users 
	//   table to first find all the user searches and then select and filter those from the external table. 
	//   The relation there would be a 1 -> 0..1 relation from LocationWeather to the the foreign table.
	//3. Using an external table to hold the the user Id (One to Many from user) and the search Ids (One to Zero..One from the Search table)
	//   This design is probably the most efficient for storage, with shallow favorites compared to all searches
	//   but it has an obvious drawback of introducing a race condition in the cascading process of the relations. 
	//   Therefore, it is omitted.
	protected boolean isFavorite = false;
	
	public boolean isFavorite() {
		return isFavorite;
	}
	public void setFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
	}
	protected List<WeatherData> forecasts;
	
	public List<WeatherData> getForecasts() {
		return forecasts;
	}
	public void setForecasts(List<WeatherData> forecasts) {
		this.forecasts = forecasts;
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