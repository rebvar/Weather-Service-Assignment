package com.rebvar.app.ws.db.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;


@Entity(name="anonym_location_weather")
public class AnonymLocationWeatherEntity extends AbstractLocationWeatherEntity implements Serializable {

	private static final long serialVersionUID = 2829389183394955264L;
	
	@Column(nullable = true)
	private String ipAddress;
	
	@Column(nullable = true)
	private String browser;

	public String getIpAddress() {
		return ipAddress;
	}

	

	/**
	 * @param ipAddress
	 * 	Extra fields to be collected for anonymous searches.
     *  Not used in this current implementation.
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	
	public String getBrowser() {
		return browser;
	}

	/**
	 * @param browser
	 * 	Extra fields to be collected for anonymous searches.
     *  Not used in this current implementation.
	 */
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	
	
	/**
	 * Link to the forecasts for this search.
	 */
	@OneToMany(mappedBy = "anonymLocationWeather",cascade=CascadeType.ALL)
	private List<AnonymForecastItemEntity> forecasts;

	public List<AnonymForecastItemEntity> getForecasts() {
		return forecasts;
	}

	public void setForecasts(List<AnonymForecastItemEntity> forecasts) {
		this.forecasts = forecasts;
	}
	
}
