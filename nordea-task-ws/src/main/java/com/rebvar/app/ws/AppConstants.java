package com.rebvar.app.ws;

public class AppConstants {
	
	public static final long EXPIRATION_TIME = 864000000;  
    public static final String TOKEN_PREFIX = "TOKEN_";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users";
    public static final String LOGIN_URL = "/users/login";
    public static final String WEATHER_CITY_ENDPOINT = "/weather/city/*";
    public static final String WEATHER_LAT_LON_ENDPOINT = "/weather/lat/*/lon/*";
    public static final String WEATHER_LON_LAT_ENDPOINT = "/weather/lon/*/lat/*";
    public static final String INVALID_AUTH_DEFAULT_VALUE = "!INVALID_AUTH_TOKEN!";
    public static final String H2_CONSOLE = "/h2-console/**";
    public static String TOKEN_SECRET;
    public static String OW_API_KEY;
    public static String OW_BASE_EXTERNAL_URL;
    public static String OW_FORECAST_EXTERNAL_URL;
}
