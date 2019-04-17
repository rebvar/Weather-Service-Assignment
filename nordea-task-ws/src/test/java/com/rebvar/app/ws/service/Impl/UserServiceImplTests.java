package com.rebvar.app.ws.service.Impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.rebvar.app.ws.db.entity.LocationWeatherEntity;
import com.rebvar.app.ws.db.entity.UserEntity;
import com.rebvar.app.ws.db.repository.LocationWeatherRepository;
import com.rebvar.app.ws.db.repository.UserRepository;
import com.rebvar.app.ws.security.SecurityUtils;
import com.rebvar.app.ws.common.dto.LocationWeatherDTO;
import com.rebvar.app.ws.common.dto.UserDTO;

@ActiveProfiles("test")
class UserServiceImplTest {

	@InjectMocks
	UserServiceImpl userService;

	@Mock
	UserRepository userRepository;

	@Mock
	BCryptPasswordEncoder bCryptEncoder;

	@Mock
	LocationWeatherRepository locationWeatherRepository;

	@Mock
	SecurityUtils sutils;

	String userId = "user1";
	String encryptedPassword = "pass1";

	UserEntity userEntity;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		userEntity = new UserEntity();
		userEntity.setId(1L);
		userEntity.setName("User1");
		userEntity.setUserId(userId);
		userEntity.setEncryptedPassword(encryptedPassword);
		userEntity.setEmail("user1@company.com");
		userEntity.setWeatherSearches(getLocationWeathersEntities());
	}

	@Test
	final void testGetUser() {

		when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

		UserDTO userDto = userService.getUserByEmail("user1@company.com");

		assertNotNull(userDto);
		assertEquals("User1", userDto.getName());

	}

	@Test
	final void testGetUser_Exception() {
		when(userRepository.findByEmail(anyString())).thenReturn(null);

		assertThrows(RuntimeException.class,

				() -> {
					userService.getUserByEmail("user1@company.com");
				}

		);
	}

	@Test
	final void testCreateUser_Exception() {
		when(userRepository.findByEmail(anyString())).thenReturn(userEntity);
		UserDTO userDto = new UserDTO();
		userDto.setWeatherSearches(getLocationWeathersDto());
		userDto.setName("User1");
		userDto.setPassword("123");
		userDto.setEmail("user1@company.com");
		userDto.setUserId("AnotherId");
		assertThrows(RuntimeException.class,

				() -> {
					userService.createUser(userDto);
				});
	}

	@Test
	final void testCreateUser() {
		when(userRepository.findByEmail(anyString())).thenReturn(null);
		when(bCryptEncoder.encode(anyString())).thenReturn(encryptedPassword);
		when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
		when(sutils.getUUID()).thenReturn(userId);

		UserDTO userDto = new UserDTO();
		userDto.setWeatherSearches(getLocationWeathersDto());
		userDto.setName("User1");
		userDto.setPassword("123");
		userDto.setEmail("user1@company.com");
		userDto.setEncryptedPassword(encryptedPassword);
		
		UserDTO storedUserDetails = userService.createUser(userDto);
		assertNotNull(storedUserDetails);
		assertEquals(userEntity.getName(), storedUserDetails.getName());
		assertNotNull(storedUserDetails.getUserId());
		assertEquals(storedUserDetails.getWeatherSearches().size(), userEntity.getWeatherSearches().size());
		verify(userRepository, times(1)).save(any(UserEntity.class));

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

//	
	private List<LocationWeatherEntity> getLocationWeathersEntities() {
		List<LocationWeatherDTO> lws = getLocationWeathersDto();

		List<LocationWeatherEntity> lwEntities = new ArrayList<LocationWeatherEntity>();

		ModelMapper mapper = new ModelMapper();
		for (LocationWeatherDTO wdto : lws) {
			lwEntities.add(mapper.map(wdto, LocationWeatherEntity.class));
		}

		return lwEntities;
	}

}
