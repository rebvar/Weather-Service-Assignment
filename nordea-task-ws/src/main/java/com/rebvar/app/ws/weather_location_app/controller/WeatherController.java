package com.rebvar.app.ws.weather_location_app.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rebvar.app.ws.AppConstants;
import com.rebvar.app.ws.common.OperationResult;
import com.rebvar.app.ws.common.dto.LocationWeatherDTO;
import com.rebvar.app.ws.security.SecurityUtils;
import com.rebvar.app.ws.service.WeatherService;
import com.rebvar.app.ws.weather_location_app.model.request.UserFavoritesRequestModel;
import com.rebvar.app.ws.weather_location_app.model.response.LocationWeatherResponseModel;

@RestController
@RequestMapping("/weather")

public class WeatherController {

	@Autowired
	WeatherService weatherService;
	
	@Autowired
	SecurityUtils sutils;
	
	
	/**
	 * @param wdto
	 * @return Generates a response model. Checks for invalid location searches.
	 */
	public LocationWeatherResponseModel dtoToResponseModel(LocationWeatherDTO wdto)
	{
		LocationWeatherResponseModel ret;
		
		if (wdto!=null)
		{
			ModelMapper mapper = new ModelMapper();
			ret = mapper.map(wdto, LocationWeatherResponseModel.class);
		}
		else
		{
			ret = new LocationWeatherResponseModel();
			ret.setLocationValid(false);
		}
		
		return ret;
	}
	
	/**
	 * @param city
	 * @param Auth_Token
	 * @return search by city: Format : /weather/city/name of the city
	 */
	@GetMapping(path = "/city/{city}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public LocationWeatherResponseModel getWeather(@PathVariable String city, @RequestHeader(name = AppConstants.HEADER_STRING, defaultValue = AppConstants.INVALID_AUTH_DEFAULT_VALUE) String Auth_Token)
	{
		String userId = sutils.getUserIdFromToken(Auth_Token);
		LocationWeatherDTO cityWeather = weatherService.getAndSaveWeather(city, userId);
		return dtoToResponseModel(cityWeather);
	}
	
	
	/**
	 * @param lat
	 * @param lon
	 * @param Auth_Token
	 * @return Search by lat and lon coordinates. Maps both options. Order is not important for lat and lon, 
	 * as it maps both 
	 */
	@GetMapping(path = {"/lat/{lat}/lon/{lon}", "/lon/{lon}/lat/{lat}"}, produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public LocationWeatherResponseModel getWeather(@PathVariable double lat, @PathVariable double lon, @RequestHeader(name = AppConstants.HEADER_STRING, defaultValue = AppConstants.INVALID_AUTH_DEFAULT_VALUE) String Auth_Token)
	{
		String userId = sutils.getUserIdFromToken(Auth_Token);
		LocationWeatherDTO locationWeather = weatherService.getAndSaveWeather(lat,lon, userId);
		return dtoToResponseModel(locationWeather);
	}
	
	
	/**
	 * @param fav
	 * @param Auth_Token
	 * @return Adds an item to the favorites. Due to the three design options for this behaviours, a POST method is used. 
	 * Can be PUT mapping as well. Returns an OperationResult value.  
	 */
	@GetMapping(path = {"/favorites"}, produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public LocationWeatherResponseModel AddToFavorites(@RequestBody UserFavoritesRequestModel fav, @RequestHeader(name = AppConstants.HEADER_STRING, defaultValue = AppConstants.INVALID_AUTH_DEFAULT_VALUE) String Auth_Token)
	{
		String userId = sutils.getUserIdFromToken(Auth_Token);
		if (userId.isEmpty())
			throw new RuntimeException("Invalid authorisation token... No user info has been specified..");
		return new ModelMapper().map(weatherService.addToFavorites(fav.getUniqueId(), userId), LocationWeatherResponseModel.class);
	}
	
	/**
	 * @param Auth_Token
	 * @return All the searches by a logged in user. Throws exception for invalid auth_token values.
	 */
	@GetMapping(path = "/searches", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public List<LocationWeatherResponseModel> getUserSearches(@RequestHeader(name = AppConstants.HEADER_STRING, defaultValue = AppConstants.INVALID_AUTH_DEFAULT_VALUE) String Auth_Token)
	{
		String userId = sutils.getUserIdFromToken(Auth_Token);
		if (userId.isEmpty())
			throw new RuntimeException("Invalid authorisation token... No user info has been specified..");
		
		List<LocationWeatherDTO> wdtos = weatherService.getAllWeatherSearches(userId);
		List<LocationWeatherResponseModel> ret = new ArrayList<LocationWeatherResponseModel>();
		ModelMapper mapper = new ModelMapper();
		wdtos.forEach(wdto->ret.add(mapper.map(wdto, LocationWeatherResponseModel.class)));
		return ret;
	}
	
	
	/**
	 * @param Auth_Token
	 * @return All the favorites for a logged in user. Throws exception for invalid auth_token values.
	 */
	@GetMapping(path = {"/favorites"}, produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public List<LocationWeatherResponseModel> getAllFavorites(@RequestHeader(name = AppConstants.HEADER_STRING, defaultValue = AppConstants.INVALID_AUTH_DEFAULT_VALUE) String Auth_Token)
	{
		String userId = sutils.getUserIdFromToken(Auth_Token);
		if (userId.isEmpty())
			throw new RuntimeException("Invalid authorisation token... No user info has been specified..");
	    List<LocationWeatherDTO> wdtos = weatherService.getAllFavorites(userId);
	    ModelMapper mapper = new ModelMapper();
	    
	    List<LocationWeatherResponseModel> retRest = new ArrayList<LocationWeatherResponseModel>();
	    wdtos.forEach(wdto->retRest.add(mapper.map(wdto, LocationWeatherResponseModel.class)));
	    return retRest;
	}
	
	
	/**
	 * @param fav
	 * @param Auth_Token
	 * @return Removes an item from the user favorites. 
	 */
	@DeleteMapping(path = {"/favorites"}, produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public LocationWeatherResponseModel deleteFromFavorites(@RequestBody UserFavoritesRequestModel fav, @RequestHeader(name = AppConstants.HEADER_STRING, defaultValue = AppConstants.INVALID_AUTH_DEFAULT_VALUE) String Auth_Token)
	{
		String userId = sutils.getUserIdFromToken(Auth_Token);
		if (userId.isEmpty())
			throw new RuntimeException("Invalid authorisation token... No user info has been specified..");
	    LocationWeatherDTO res = weatherService.deleteFromFavorites(fav.getUniqueId(), userId);
	    return new ModelMapper().map(res, LocationWeatherResponseModel.class);
	}
	
	
	/**
	 * @param id
	 * @param Auth_Token
	 * @return Returns a weather search from the two tables. If the user is valid and logged in, from LocationWeather. 
	 * if not from the Anonymous table. Accepts a public unique Id.
	 */
	@GetMapping(path="/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public LocationWeatherResponseModel getSearchedWeather(@PathVariable String id, @RequestHeader(name = AppConstants.HEADER_STRING, defaultValue = AppConstants.INVALID_AUTH_DEFAULT_VALUE) String Auth_Token)
	{
		String userId = sutils.getUserIdFromToken(Auth_Token);
		LocationWeatherDTO locationWeather = weatherService.getSavedWeather(id, userId);
		return dtoToResponseModel(locationWeather);
	}
	
}
