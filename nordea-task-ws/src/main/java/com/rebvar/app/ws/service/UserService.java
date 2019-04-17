package com.rebvar.app.ws.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.rebvar.app.ws.common.dto.UserDTO;

/**
 * @author sehossei
 * User Service. In charge of user operations.
 */
public interface UserService extends UserDetailsService {
	public UserDTO getUserByEmail(String email);
	public UserDTO getUserByUserId(String id);
	public UserDTO createUser(UserDTO inputUserData);
	public UserDTO updateUser(UserDTO userDto);
}
