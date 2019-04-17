package com.rebvar.app.ws.weather_location_app.model.response;

import java.util.List;
import java.util.Set;

import com.rebvar.app.ws.common.models.AbstractLocationWeatherModel;
import com.rebvar.app.ws.common.models.WeatherData;

public class LocationWeatherResponseModel extends AbstractLocationWeatherModel {

	protected List<WeatherData> forecasts;
	
	public List<WeatherData> getForecasts() {
		return forecasts;
	}
	public void setForecasts(List<WeatherData> forecasts) {
		this.forecasts = forecasts;
	}
	
}
