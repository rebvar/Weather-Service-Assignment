package com.rebvar.app.ws.weather_location_app.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import com.rebvar.app.ws.service.Impl.UserServiceImpl;
import com.rebvar.app.ws.common.dto.LocationWeatherDTO;
import com.rebvar.app.ws.common.dto.UserDTO;
import com.rebvar.app.ws.security.SecurityUtils;
import com.rebvar.app.ws.weather_location_app.controller.UserController;
import com.rebvar.app.ws.weather_location_app.model.request.UserLoginRequestModel;
import com.rebvar.app.ws.weather_location_app.model.response.LocationWeatherResponseModel;
import com.rebvar.app.ws.weather_location_app.model.response.UserDataResponseModel;
@ActiveProfiles("test")
class UserControllerTest {

	@InjectMocks
	UserController userController;
	
	@Mock
	UserServiceImpl userService;
	
	@Mock
	SecurityUtils sutils;
	
	UserDTO userDto;
	
	final String USER_ID = "user1";
	final String USER_TOKEN = "token"; 
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		userDto = new UserDTO();
        userDto.setName("User1");
        
        userDto.setEmail("user1@company.com");
        userDto.setUserId(USER_ID);
        userDto.setWeatherSearches(getLocationWeathersDto());
        userDto.setEncryptedPassword("Pass1");
		
	}

	@Test
	final void testGetUser() {
	    when(userService.getUserByUserId(anyString())).thenReturn(userDto);
	    when(sutils.getUserIdFromToken(anyString())).thenReturn(USER_TOKEN);
	    UserDataResponseModel resp = userController.getUser(USER_ID);
	    assertNotNull(resp);
	    assertEquals(USER_ID, resp.getUserId());
	    assertEquals(userDto.getName(), resp.getName());
	}
	
	
	private List<LocationWeatherDTO> getLocationWeathersDto() {
		
		LocationWeatherDTO wDto = new LocationWeatherDTO();

		wDto.setCity("New York");
		wDto.setLat(10);
		wDto.setLon(20);
		wDto.setCountryCode("US");
		Date date = new Date(2010, 1, 1, 0, 0, 0);
		wDto.setDate(date);
		wDto.setTemprature(320);
		wDto.setExtra("");
		wDto.setLocationValid(true);
		wDto.setTimestamp(date.getTime());

		LocationWeatherDTO wDto2 = new LocationWeatherDTO();
		wDto2.setCity("Oulu");
		wDto2.setLat(22);
		wDto2.setLon(33);
		wDto2.setCountryCode("FI");
		date = new Date(2019, 1, 1, 0, 0, 0);
		wDto2.setDate(date);
		wDto2.setTemprature(1);
		wDto2.setExtra("");
		wDto2.setLocationValid(true);
		wDto2.setTimestamp(date.getTime());

		List<LocationWeatherDTO> locationWeathers = new ArrayList<>();
		locationWeathers.add(wDto);
		locationWeathers.add(wDto2);

		return locationWeathers;
	}

}
