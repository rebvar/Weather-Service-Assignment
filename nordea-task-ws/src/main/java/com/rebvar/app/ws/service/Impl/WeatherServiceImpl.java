package com.rebvar.app.ws.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rebvar.app.ws.AppConstants;
import com.rebvar.app.ws.common.OpenWeatherMapParser;
import com.rebvar.app.ws.common.OperationResult;
import com.rebvar.app.ws.common.dto.LocationWeatherDTO;
import com.rebvar.app.ws.common.models.WeatherData;
import com.rebvar.app.ws.db.entity.AnonymForecastItemEntity;
import com.rebvar.app.ws.db.entity.AnonymLocationWeatherEntity;
import com.rebvar.app.ws.db.entity.ForecastItemEntity;
import com.rebvar.app.ws.db.entity.LocationWeatherEntity;
import com.rebvar.app.ws.db.entity.UserEntity;
import com.rebvar.app.ws.db.repository.AnonymLocationWeatherRepository;
import com.rebvar.app.ws.db.repository.LocationWeatherRepository;
import com.rebvar.app.ws.db.repository.UserRepository;
import com.rebvar.app.ws.security.SecurityUtils;
import com.rebvar.app.ws.service.WeatherService;

import java.util.ArrayList;
import java.util.List;


import org.json.*;
import org.modelmapper.ModelMapper;

/**
 * @author sehossei
 *
 * Implements the WeatherService interface as service. Performs various operations for weather including receiving the 
 * weather data and presenting the results through Data transfer objects.
 */

@Service
public class WeatherServiceImpl implements WeatherService{

	
	//Addresses for easier use in the code.
	private final String APIKEY = AppConstants.OW_API_KEY;
	private final String BASE_URL = AppConstants.OW_BASE_EXTERNAL_URL;
	private final String FORECAST_URL = AppConstants.OW_FORECAST_EXTERNAL_URL;
	
	//CrudRepository for various operations on weather data
	@Autowired
	LocationWeatherRepository locationWeatherRepository;
	
	//CrudRepository for various operations on weather data for anonymous use.
	@Autowired
	AnonymLocationWeatherRepository anonymLocationWeatherRepository;
	
	
	//Accessing the userRepository to save the searches and manage the favorite operations.
	@Autowired
	UserRepository userRepository;
	
	
	//for generating unique ids. 
	@Autowired 
	SecurityUtils sutils;
	
	
	
	
	/**
	 * @param uri
	 * 
	 * Returns the body of a GET request to the specified uri
	 * 
	 * @return JSON String result of the GET operation 
	 */
	public String getJSONResponse(String uri)
	{
		String jsonResponse; 
	    RestTemplate restTemplate = new RestTemplate();
	    try
	    {
	    	jsonResponse = restTemplate.getForObject(uri, String.class);
		}
		catch (Exception ex)
		{
			throw new RuntimeException("Error retrieving the data...");
		}
	    
	    return jsonResponse;
	}
	
	
	/**
	 * @param uri
	 * Converts the acquired weather info from owapi to LocationWeatherDTO
	 * @return
	 */
	public LocationWeatherDTO getWeatherFromURI(String uri)
	{

	    String jsonResponse = getJSONResponse(uri);
	    OpenWeatherMapParser owParser = new OpenWeatherMapParser(jsonResponse);
	    return owParser.toWeatherDTO();
	}
	
	
	
	/**
	 * @param uri
	 * Acquires the forecast data. Forecast data elements are cast to WeatherData which is the base model for AbstractLocationWeatherModel 
	 * Anonim version.
	 * @return a list of weather data objects which holds various weather information data including temprature, humidity, ... 
	 * and include a timestamp
	 */
	public List<WeatherData> getForecastData(String uri)
	{
		String jsonResponse = getJSONResponse(uri);
		List<WeatherData> ret = new ArrayList<>();
		
		//Create a parser. Reuse it for each forecast.
		OpenWeatherMapParser parser = new OpenWeatherMapParser();
		JSONArray forecastListData = new JSONObject(jsonResponse).getJSONArray("list");
		
		//Extract weather data from the JSON array using lambda.
		forecastListData.forEach(obj -> ret.add(parser.set((JSONObject)obj).toWeatherData()));
		
		return ret;
	}
	
	
	/**
	 * @param wdto
	 * @param forecastData
	 * 
	 * Saves the retrieved data and forecast to the database. This is for anonymous searches.
	 * 
	 * @return a location weather data which include forecasts as a field to be persisted.
	 */
	public LocationWeatherDTO saveToAnonymLocationWeatherRepo(LocationWeatherDTO wdto,List<WeatherData> forecastData)
	{
		ModelMapper mapper = new ModelMapper();
		AnonymLocationWeatherEntity alw = mapper.map(wdto, AnonymLocationWeatherEntity.class);
		alw.setUniqueId(sutils.getUUID());
		
		List<AnonymForecastItemEntity> forecasts = new ArrayList<AnonymForecastItemEntity>();
		
		forecastData.forEach(item -> forecasts.add(mapper.map(item, AnonymForecastItemEntity.class)));
		forecasts.forEach(item->item.setAnonymLocationWeather(alw));
		
		alw.setForecasts(forecasts);
		
		anonymLocationWeatherRepository.save(alw);
		return mapper.map(alw, LocationWeatherDTO.class);
		
	}
	
	
	
	/**
	 * @param wdto
	 * @param forecastData
	 * 
	 * Saves the retrieved data and forecast to the database. This is for logged in users searches.
	 * 
	 * @return a location weather data which include forecasts as a field to be persisted.
	 */
	public LocationWeatherDTO saveToUserLocationWeatherRepo(UserEntity u, LocationWeatherDTO wdto,List<WeatherData> forecastData)
	{
		ModelMapper mapper = new ModelMapper();
		
		LocationWeatherEntity lw = mapper.map(wdto, LocationWeatherEntity.class);
		lw.setUniqueId(sutils.getUUID());
		lw.setUserDetails(u);
		
		List<ForecastItemEntity> forecasts = new ArrayList<ForecastItemEntity>();
		forecastData.forEach(item -> forecasts.add(mapper.map(item, ForecastItemEntity.class)));
		forecasts.forEach(item->item.setLocation_weather(lw));
		lw.setForecasts(forecasts);
		
		locationWeatherRepository.save(lw);
		return mapper.map(lw, LocationWeatherDTO.class);
		
	}
	
	
	
	/**
	 * @param uri
	 * @param forecastUri
	 * @param userId
	 * Handles saving and returning the data to the database and to the request.
	 * @return the retrieved LocationWeatherDTO object.
	 */
	
	public LocationWeatherDTO getAndSaveToRepository(String uri, String forecastUri, String userId)
	{
		LocationWeatherDTO wdto = getWeatherFromURI(uri);
		
		List<WeatherData> forecastData = getForecastData(forecastUri);
		
		if (userId.isEmpty())
		{
			return saveToAnonymLocationWeatherRepo(wdto, forecastData);
		}
		else
		{
			UserEntity u = userRepository.findByUserId(userId);
			if (u == null)
			{
				throw new RuntimeException("UserId is not valid");
			}			
			return saveToUserLocationWeatherRepo(u, wdto, forecastData);
		}
	}
	
	
	
	/**
	 * Search based on city
	 */
	@Override
	public LocationWeatherDTO getAndSaveWeather(String city, String userId) {
		final String uri = BASE_URL+"q="+city+"&appid="+APIKEY;
		final String furi = FORECAST_URL+"q="+city+"&appid="+APIKEY;
		return getAndSaveToRepository(uri,furi, userId);
	}
	
	
	
	/**
	 * Search based on Coordinates.
	 */
	@Override
	public LocationWeatherDTO getAndSaveWeather(double lat , double lon, String userId) {
		
		final String uri = BASE_URL + "lat="+String.valueOf(lat)+"&lon="+String.valueOf(lon)+"&appid="+APIKEY;
		final String furi = FORECAST_URL + "lat="+String.valueOf(lat)+"&lon="+String.valueOf(lon)+"&appid="+APIKEY;
		return getAndSaveToRepository(uri,furi, userId);
	}
	
	/**
	 * Adds a search to the user favorites. Changing the favorite flag to 1.
	 */
	@Override
	public LocationWeatherDTO addToFavorites(String publicWeatherId, String publicUserId)
	{
		UserEntity user = userRepository.findByUserId(publicUserId);
		if (user==null)
			throw new RuntimeException("Invalid User Id");
		LocationWeatherEntity location_weather = locationWeatherRepository.findByUniqueId(publicWeatherId);
		if (location_weather==null)
			throw new RuntimeException("No weather infor for the specified id");
		
		if (location_weather.getUserDetails().getId()!=user.getId())
		{
			throw new RuntimeException("Access denied. The weather query belongs to another user..");
		}
		
		location_weather.setFavorite(true);
		locationWeatherRepository.save(location_weather);
		
		
		return new ModelMapper().map(location_weather, LocationWeatherDTO.class);
	}

	
	/**
	 * Retrieves the data for a saved search instance, based on the search's public uniqueId.
	 */
	@Override
	public LocationWeatherDTO getSavedWeather(String uniqueId, String publicUserId) {
	
		UserEntity user = userRepository.findByUserId(publicUserId);
		if (user==null)
			throw new RuntimeException("Invalid User Id");
		LocationWeatherEntity location_weather = locationWeatherRepository.findByUniqueId(uniqueId);
		if (location_weather==null)
			throw new RuntimeException("No weather infor for the specified id");
		
		if (location_weather.getUserDetails().getId()!=user.getId())
		{
			throw new RuntimeException("Access denied. The weather query belongs to another user..");
		}
		
		LocationWeatherDTO wdto = new ModelMapper().map(location_weather, LocationWeatherDTO.class);
		
		return wdto;
	}
	
	
	/** 
	 * Deletes an item from favorites by setting the favorite flag to false
	 */
	@Override
	public LocationWeatherDTO deleteFromFavorites(String uniqueId, String publicUserId) {
	
		UserEntity user = userRepository.findByUserId(publicUserId);
		if (user==null)
			throw new RuntimeException("Invalid User Id");
		LocationWeatherEntity location_weather = locationWeatherRepository.findByUniqueId(uniqueId);
		if (location_weather==null)
			throw new RuntimeException("No weather infor for the specified id");
		
		if (location_weather.getUserDetails().getId()!=user.getId())
		{
			throw new RuntimeException("Access denied. The weather query belongs to another user..");
		}
		
		location_weather.setFavorite(false);
		
		
		
		locationWeatherRepository.save(location_weather);
		return new ModelMapper().map(location_weather, LocationWeatherDTO.class);
	}
	
	
	/**
	 * List of the user favorites
	 */
	@Override
	public List<LocationWeatherDTO> getAllFavorites(String userId) {
		UserEntity u = userRepository.findByUserId(userId);
		if (u == null) throw new RuntimeException("User not found");
		
		List<LocationWeatherDTO> ret = new ArrayList<LocationWeatherDTO>();
		
		Iterable<LocationWeatherEntity> lwEntities = locationWeatherRepository.findAllByUserDetailsAndIsFavorite(u, true);
        
		ModelMapper mapper = new ModelMapper();
		lwEntities.forEach(lwEntity-> ret.add(mapper.map(lwEntity, LocationWeatherDTO.class)));		
		return ret;
	}
	
	
	/**
	 * List of all searches by the user
	 */
	@Override
	public List<LocationWeatherDTO> getAllWeatherSearches(String userId) {
		UserEntity u = userRepository.findByUserId(userId);
		if (u == null) throw new RuntimeException("User not found");
		List<LocationWeatherDTO> ret = new ArrayList<LocationWeatherDTO>();
		Iterable<LocationWeatherEntity> lwEntities = locationWeatherRepository.findAllByUserDetails(u);
        ModelMapper mapper = new ModelMapper();
		lwEntities.forEach(lwEntity-> ret.add(mapper.map(lwEntity, LocationWeatherDTO.class)));
		return ret;
	}
	
}
