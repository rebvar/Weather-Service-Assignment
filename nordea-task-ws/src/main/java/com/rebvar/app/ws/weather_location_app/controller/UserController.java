package com.rebvar.app.ws.weather_location_app.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rebvar.app.ws.AppConstants;
import com.rebvar.app.ws.common.dto.UserDTO;
import com.rebvar.app.ws.security.SecurityUtils;
import com.rebvar.app.ws.service.UserService;
import com.rebvar.app.ws.weather_location_app.model.request.UserDataRequestModel;
import com.rebvar.app.ws.weather_location_app.model.response.UserDataResponseModel;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	SecurityUtils sutils;

	
	
	/**
	 * @param Auth_Token
	 * @return Returns a logged in user data. Reads the auth_token to find valid user.
	 */
	@GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserDataResponseModel getUser(@RequestHeader(name = AppConstants.HEADER_STRING, defaultValue = AppConstants.INVALID_AUTH_DEFAULT_VALUE) String Auth_Token)
	{
		String userId = sutils.getUserIdFromToken(Auth_Token);
		if (userId.isEmpty())
			throw new RuntimeException("Invalid authorisation token... No user info has been specified..");
		
		ModelMapper mapper = new ModelMapper();
		UserDataResponseModel resp = mapper.map(userService.getUserByUserId(userId), UserDataResponseModel.class); 
		return resp;
	}
	
	/**
	 * @param userdata
	 * @return registers a new user.
	 */
	@PostMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserDataResponseModel createUser(@RequestBody UserDataRequestModel userdata)
	{	
		ModelMapper mapper = new ModelMapper();
		UserDTO userDto = mapper.map(userdata,UserDTO.class);
		UserDTO createdUser = userService.createUser(userDto); 
		UserDataResponseModel ret = mapper.map(createdUser, UserDataResponseModel.class);
		return ret;
	}
	
	
	/**
	 * @param userdata
	 * @param Auth_Token
	 * @return Updates a user. First checks the identity with the auth_token.
	 */
	@PutMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserDataResponseModel updateUser(@RequestBody UserDataRequestModel userdata, @RequestHeader(name = AppConstants.HEADER_STRING, defaultValue = AppConstants.INVALID_AUTH_DEFAULT_VALUE) String Auth_Token)
	{
		String userId = sutils.getUserIdFromToken(Auth_Token);
		if (userId.isEmpty())
			throw new RuntimeException("Invalid authorisation token... No user info has been specified..");
		
		ModelMapper mapper = new ModelMapper();
		UserDTO userDto = mapper.map(userdata,UserDTO.class);
		userDto.setUserId(userId);
		UserDTO updatedUser = userService.updateUser(userDto); 
		UserDataResponseModel ret = mapper.map(updatedUser, UserDataResponseModel.class);
		return ret;
	}
	
}
