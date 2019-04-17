package com.rebvar.app.ws.service;

import java.util.List;

import com.rebvar.app.ws.common.OperationResult;
import com.rebvar.app.ws.common.dto.LocationWeatherDTO;

/**
 * @author sehossei
 * Weather Service. In charge of search, storage and retrieval of the location weather processes.
 */
/**
 * @author sehossei
 *
 */
public interface WeatherService {

	
	/**
	 * @param location
	 * @param userId
	 * @return Saves the search and returns the result
	 */
	public LocationWeatherDTO getAndSaveWeather(String location, String userId);
	
	
	/**
	 * @param lat
	 * @param lon
	 * @param userId
	 * @return Saves the search and returns the result
	 */
	public LocationWeatherDTO getAndSaveWeather(double lat, double lon, String userId);
	
	public LocationWeatherDTO addToFavorites(String publicWeatherId, String publicUserId);
	
	
	/**
	 * @param uniqueId
	 * @param publicUserId
	 * @return Retrieves a saved search by unique public id
	 */
	public LocationWeatherDTO getSavedWeather(String uniqueId, String publicUserId);
	
	public LocationWeatherDTO deleteFromFavorites(String uniqueId, String publicUserId);
	
	
	/**
	 * @param publicUserId
	 * @return List of user favorites 
	 */
	public List<LocationWeatherDTO> getAllFavorites(String publicUserId);
	
	/**
	 * @param userId
	 * @return List of user searches
	 */
	public List<LocationWeatherDTO> getAllWeatherSearches(String userId);
}
