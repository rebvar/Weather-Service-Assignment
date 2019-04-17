package com.rebvar.app.ws.common;

public class WeatherUtils {

	public static final double KEL_MIN = -273.15;
	public static double getTempratureCelcius(double tempKelvin)
	{
		return tempKelvin+KEL_MIN;
	}
	
}
