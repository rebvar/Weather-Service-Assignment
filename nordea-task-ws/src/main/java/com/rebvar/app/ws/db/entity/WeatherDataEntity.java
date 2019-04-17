package com.rebvar.app.ws.db.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class WeatherDataEntity {
	@Column(nullable = false)
	protected long timestamp;
	@Column(nullable = false)
	protected double temprature;
	@Column(nullable = false)
	protected double humidity;
	@Column(nullable = false)
	protected double skyCloud;
	@Column(nullable = false)
	protected double windDirection;
	@Column(nullable = false)
	protected double windSpeed;
	@Column(nullable = false)
	protected double pressure;
	
	public double getTemprature() {
		return temprature;
	}
	
	public void setTemprature(double temprature) {
		this.temprature = temprature;
	}
	public double getHumidity() {
		return humidity;
	}
	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}
	public double getSkyCloud() {
		return skyCloud;
	}
	public void setSkyCloud(double skyCloud) {
		this.skyCloud = skyCloud;
	}
	public double getWindDirection() {
		return windDirection;
	}
	public void setWindDirection(double windDirection) {
		this.windDirection = windDirection;
	}
	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public double getWindSpeed() {
		return windSpeed;
	}
	public void setWindSpeed(double windSpeed) {
		this.windSpeed = windSpeed;
	}
	public double getPressure() {
		return pressure;
	}
	public void setPressure(double pressure) {
		this.pressure = pressure;
	}
}
