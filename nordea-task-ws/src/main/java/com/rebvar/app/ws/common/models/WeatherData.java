package com.rebvar.app.ws.common.models;


/**
 * @author sehossei
 * Holds the weather data fields. 
 */
public class WeatherData {
	
	
	protected long timestamp;
	protected double temprature;
	protected double humidity;
	protected double skyCloud;
	protected double windDirection;
	protected double windSpeed;
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
