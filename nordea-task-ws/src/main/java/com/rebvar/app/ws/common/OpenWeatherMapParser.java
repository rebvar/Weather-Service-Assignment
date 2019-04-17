package com.rebvar.app.ws.common;

import java.util.Date;
import org.json.JSONObject;
import com.rebvar.app.ws.common.dto.LocationWeatherDTO;
import com.rebvar.app.ws.common.models.WeatherData;

public class OpenWeatherMapParser {
	
	private JSONObject owJSonObject;
	
	public OpenWeatherMapParser()
	{
		owJSonObject = null;
	}
	
	public OpenWeatherMapParser(String jsonString)
	{
		set(jsonString);
	}
	
	public OpenWeatherMapParser set(String jsonString)
	{
		owJSonObject = null;
		owJSonObject = new JSONObject(jsonString);
		return this;
	}
	
	public OpenWeatherMapParser set(JSONObject object)
	{
		owJSonObject = null;
		owJSonObject = object;
		return this;
	}
	
	public double getTemprature()
	{
		return owJSonObject.getJSONObject("main").getDouble("temp");
	}
	
	public double getCoordLat()
	{
		return owJSonObject.getJSONObject("coord").getDouble("lat");
	}
	
	public double getCoordLon()
	{
		return owJSonObject.getJSONObject("coord").getDouble("lon");
	}
	
	public String getCountryCode()
	{
		return owJSonObject.getJSONObject("sys").getString("country");
	}
	
	public String getCity()
	{
		return owJSonObject.getString("name");
	}
	
	public long getTimestamp()
	{
		return owJSonObject.getLong("dt");
	}
	
	
	public double getHumidity() {
		return owJSonObject.getJSONObject("main").getDouble("humidity");
	}

	public double getSkyCloud() {
		return owJSonObject.getJSONObject("clouds").getDouble("all");
	}

	public double getWindDirection() {
		return owJSonObject.getJSONObject("wind").getDouble("deg");
	}

	public double getWindSpeed() {
		return owJSonObject.getJSONObject("wind").getDouble("speed");
	}

	public double getPressure() {
		return owJSonObject.getJSONObject("main").getDouble("pressure");
	}
	
	
	public LocationWeatherDTO toWeatherDTO()
	{
		LocationWeatherDTO ret = new LocationWeatherDTO();
	    ret.setTemprature(getTemprature());
	    
	    ret.setLat(getCoordLat());
	    ret.setLon(getCoordLon());
	    ret.setLocationValid(true);
	    ret.setCity(getCity());
	    try {
	    	ret.setCountryCode(getCountryCode());
	    }
	    catch(Exception ex)
	    {
	    	ret.setCountryCode("Not available");
	    }
	    
	    ret.setHumidity(getHumidity());
	    ret.setPressure(getPressure());
	    ret.setWindDirection(getWindDirection());
	    ret.setWindSpeed(getWindSpeed());
	    ret.setSkyCloud(getSkyCloud());
	    ret.setTimestamp(getTimestamp());
	    //Expects milliseconds. Multiply by 1000 to convert seconds to milliseconds.
	    Date d = new Date(ret.getTimestamp()*1000);
	    ret.setDate(d);
	    return ret;
	}
	
	
	public WeatherData toWeatherData()
	{
		WeatherData ret = new WeatherData();
	    ret.setTemprature(getTemprature());
	    
	    ret.setHumidity(getHumidity());
	    ret.setPressure(getPressure());
	    ret.setWindDirection(getWindDirection());
	    ret.setWindSpeed(getWindSpeed());
	    ret.setSkyCloud(getSkyCloud());
	    ret.setTimestamp(getTimestamp());
	    return ret;
	}
}
