package com.rebvar.app.ws.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rebvar.app.ws.common.dto.UserDTO;
import com.rebvar.app.ws.db.entity.UserEntity;
import com.rebvar.app.ws.db.repository.UserRepository;
import com.rebvar.app.ws.security.SecurityUtils;
import com.rebvar.app.ws.service.UserService;

import java.util.ArrayList;


import org.modelmapper.ModelMapper;

@Service
public class UserServiceImpl implements UserService{

	@Autowired 
	UserRepository userRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptEncoder;
	
	@Autowired 
	SecurityUtils sutils;
	
	@Override
	public UserDTO createUser(UserDTO inputUserData) {
				
		//Check if exists
		if (userRepository.findByEmail(inputUserData.getEmail())!=null)
			throw new RuntimeException("Email Address Already Exists. Please Login.");
		ModelMapper mapper = new ModelMapper();
		UserEntity userEntity =  mapper.map(inputUserData, UserEntity.class);
		userEntity.setUserId(sutils.getUUID());
		userEntity.setEncryptedPassword(bCryptEncoder.encode(inputUserData.getPassword()));
		
		return mapper.map(userRepository.save(userEntity), UserDTO.class);
	}

	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity u = userRepository.findByEmail(email);
		if (u == null)
			throw new UsernameNotFoundException("Entered email not found. Please register first.");
		return new User(u.getUserId(), u.getEncryptedPassword(), new ArrayList<>());
	}

	@Override
	public UserDTO getUserByEmail(String email) {
		UserEntity u = userRepository.findByEmail(email);
		if (u == null) throw new RuntimeException("User not found");
		ModelMapper mapper = new ModelMapper();
		return mapper.map(u, UserDTO.class);
	}
	
	
	@Override
	public UserDTO getUserByUserId(String publicUserId) {
		UserEntity u = userRepository.findByUserId(publicUserId);
		if (u == null) throw new RuntimeException("User not found");
		ModelMapper mapper = new ModelMapper();
		return mapper.map(u, UserDTO.class);
	}

	
	@Override
	public UserDTO updateUser(UserDTO userDto) {
		UserEntity u = userRepository.findByUserId(userDto.getUserId());
		if (u.getEmail().compareTo(userDto.getEmail())!=0)
			throw new RuntimeException("Invalid User Data!"); 
		u.setName(userDto.getName());
		u = userRepository.save(u);
		return new ModelMapper().map(u, UserDTO.class);
	}
}
