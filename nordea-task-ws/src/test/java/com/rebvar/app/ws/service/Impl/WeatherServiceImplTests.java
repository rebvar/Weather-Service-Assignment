package com.rebvar.app.ws.service.Impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.rebvar.app.ws.db.entity.AnonymForecastItemEntity;
import com.rebvar.app.ws.db.entity.AnonymLocationWeatherEntity;
import com.rebvar.app.ws.db.entity.ForecastItemEntity;
import com.rebvar.app.ws.db.entity.LocationWeatherEntity;
import com.rebvar.app.ws.db.entity.UserEntity;
import com.rebvar.app.ws.db.repository.AnonymLocationWeatherRepository;
import com.rebvar.app.ws.db.repository.LocationWeatherRepository;
import com.rebvar.app.ws.db.repository.UserRepository;
import com.rebvar.app.ws.security.SecurityUtils;
import com.rebvar.app.ws.common.dto.LocationWeatherDTO;
import com.rebvar.app.ws.common.models.WeatherData;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
class WeatherServiceImplTest {

	@Spy
	@InjectMocks
	WeatherServiceImpl weatherService;

	@Mock
	LocationWeatherRepository locationWeatherRepository;
	
	@Mock
	AnonymLocationWeatherRepository anonymLocationWeatherRepository;
		
	@Mock
	UserRepository userRepository;
	
	@Mock 
	SecurityUtils sutils;
	
	
	String userId = "user1";
	String encryptedPassword = "pass1";

	UserEntity user1, user2;
	LocationWeatherEntity lwEntity;
	LocationWeatherEntity lwEntity1, lwEntity2;
	AnonymLocationWeatherEntity anLwEntity;
	AnonymForecastItemEntity anforecastItem;
	AnonymLocationWeatherEntity anonymWeatherEntity;
	List<LocationWeatherEntity> U1lwEntites;
	List<LocationWeatherEntity> U2lwEntites;
	
	List<ForecastItemEntity> forecastEntities;
	ForecastItemEntity forecastItem;
	List<AnonymForecastItemEntity> anForecastEntities;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		
		user1 = new UserEntity();
	    user1.setName("User1");
	    user1.setUserId("User1RandomId1");
	    user1.setEncryptedPassword("User1Password");
	    user1.setEmail("user1@company.com");
	    
		lwEntity = new LocationWeatherEntity();
		lwEntity.setCity("City1");
		lwEntity.setLat(1);
		lwEntity.setLon(1);
		lwEntity.setCountryCode("FI");
		Date date=new Date(2000,1,1,0,0,0);
		lwEntity.setDate(date);
		lwEntity.setTemprature(1);
		lwEntity.setExtra("");
		lwEntity.setLocationValid(true);
		lwEntity.setUniqueId("UniqueId1");
		lwEntity.setTimestamp(date.getTime());
		lwEntity.setUserDetails(user1);
		U1lwEntites = new ArrayList<>();
		U1lwEntites.add(lwEntity);
		
		user1.setWeatherSearches(U1lwEntites);
	
		user2 = new UserEntity();
		user2.setName("User2");
		user2.setUserId("User2RandomId2");
		user2.setEncryptedPassword("User2Password");
		user2.setEmail("user2@company.com");
		
		
		U2lwEntites = new ArrayList<>();
		lwEntity1 = new LocationWeatherEntity();
		lwEntity1.setCity("City2");
		lwEntity1.setLat(100);
		lwEntity1.setLon(500);
		lwEntity1.setCountryCode("GB");
		date = new Date(2019, 1, 1, 0, 0, 0);
		lwEntity1.setDate(date);
		lwEntity1.setTemprature(260);
		lwEntity1.setFavorite(true);
		lwEntity1.setExtra("");
		lwEntity1.setLocationValid(true);
		lwEntity1.setUniqueId("UniqueId4");
		lwEntity1.setTimestamp(date.getTime());
		lwEntity1.setUserDetails(user2);
		
		
		
		forecastItem = new ForecastItemEntity();
		forecastItem.setHumidity(88);
		forecastItem.setPressure(888);
		forecastItem.setSkyCloud(88);
		forecastItem.setTemprature(88.88);
		forecastItem.setTimestamp(date.getTime());
		forecastItem.setWindDirection(88);
		forecastItem.setWindSpeed(88);
		forecastItem.setLocation_weather(lwEntity1);
		
		forecastEntities = new ArrayList<ForecastItemEntity>();
		forecastEntities.add(forecastItem);
		lwEntity1.setForecasts(forecastEntities);
		
		U2lwEntites.add(lwEntity1);

		lwEntity2 = new LocationWeatherEntity();
		lwEntity2.setCity("New York");
		lwEntity2.setLat(10);
		lwEntity2.setLon(20);
		lwEntity2.setCountryCode("US");
		date = new Date(2010, 1, 1, 0, 0, 0);
		lwEntity2.setDate(date);
		lwEntity2.setTemprature(320);
		lwEntity2.setExtra("");
		lwEntity2.setLocationValid(true);
		lwEntity2.setUniqueId("UniqueId3");
		lwEntity2.setTimestamp(date.getTime());
		lwEntity2.setUserDetails(user2);
		U2lwEntites.add(lwEntity2);

		user2.setWeatherSearches(U2lwEntites);

		anLwEntity = new AnonymLocationWeatherEntity();
		anLwEntity.setCity("New York");
		anLwEntity.setLat(10);
		anLwEntity.setLon(20);
		anLwEntity.setCountryCode("US");
		date = new Date();
		anLwEntity.setDate(new Date(date.getDate()-10000));
		anLwEntity.setTemprature(320);
		anLwEntity.setExtra("");
		anLwEntity.setLocationValid(true);
		anLwEntity.setUniqueId("UniqueId3");
		anLwEntity.setTimestamp(date.getTime());

		anforecastItem = new AnonymForecastItemEntity();
		anforecastItem.setHumidity(99);
		anforecastItem.setPressure(999);
		anforecastItem.setSkyCloud(99);
		anforecastItem.setTemprature(99.99);
		anforecastItem.setTimestamp(date.getTime());
		anforecastItem.setWindDirection(99);
		anforecastItem.setWindSpeed(99);
		anforecastItem.setAnonymLocationWeather(anLwEntity);
		
		
		anForecastEntities = new ArrayList<AnonymForecastItemEntity>();
		anForecastEntities.add(anforecastItem);
		anLwEntity.setForecasts(anForecastEntities);
	}

	@Test
	final void testGetAndSaveToRepository4User() {
		doReturn("Hi").when(weatherService).getJSONResponse(anyString());
		ModelMapper mapper = new ModelMapper();
		LocationWeatherDTO lwDto = mapper.map(lwEntity, LocationWeatherDTO.class); 
		lwDto.setUniqueId(null);
		ArrayList<WeatherData> wdata = new ArrayList<>();
		forecastEntities.forEach(fr->wdata.add(mapper.map(fr,WeatherData.class)));
		doReturn(lwDto).when(weatherService).getWeatherFromURI(anyString());
		
		LocationWeatherDTO dto = weatherService.getWeatherFromURI("Hi");
		assertNotNull(dto);
		assertEquals(dto.getCity(), lwEntity.getCity());
		
		doReturn(wdata).when(weatherService).getForecastData(anyString());
		doReturn(lwEntity.getUniqueId()).when(sutils).getUUID();
		doReturn(lwEntity).when(locationWeatherRepository).save(any(LocationWeatherEntity.class));
		doReturn(user1).when(userRepository).findByUserId(anyString());
		dto = weatherService.getAndSaveWeather("City1", "User1RandomId1");
		assertNotNull(dto);
		assertEquals(dto.getUniqueId(), lwEntity.getUniqueId());
		assertEquals(dto.getCity(), lwEntity.getCity());
		assertNotNull(dto.getForecasts());
		assertEquals(dto.getForecasts().size(), 1);
	}

	@Test
	final void testGetAndSaveToRepositoryAnonym() {
		
		doReturn("Hi").when(weatherService).getJSONResponse(anyString());
		ModelMapper mapper = new ModelMapper();
		LocationWeatherDTO lwDto = mapper.map(anLwEntity, LocationWeatherDTO.class); 
		lwDto.setUniqueId(null);
		ArrayList<WeatherData> wdata = new ArrayList<>();
		anForecastEntities.forEach(fr->wdata.add(mapper.map(fr,WeatherData.class)));
		doReturn(lwDto).when(weatherService).getWeatherFromURI(anyString());
		
		LocationWeatherDTO dto = weatherService.getWeatherFromURI("Hi");
		assertNotNull(dto);
		assertEquals(dto.getCity(), anLwEntity.getCity());
		
		doReturn(wdata).when(weatherService).getForecastData(anyString());
		doReturn(anLwEntity.getUniqueId()).when(sutils).getUUID();
		doReturn(anLwEntity).when(anonymLocationWeatherRepository).save(any(AnonymLocationWeatherEntity.class));
		dto = weatherService.getAndSaveWeather("New York","");
		assertNotNull(dto);
		assertEquals(dto.getUniqueId(), anLwEntity.getUniqueId());
		assertEquals(dto.getCity(), anLwEntity.getCity());
		assertNotNull(dto.getForecasts());
		assertEquals(dto.getForecasts().size(), 1);
		assertEquals(dto.getForecasts().get(0).getTemprature(), 99.99);		
	}
	
	
	@Test
	public void testAddToFavorite()
	{
		doReturn(user2).when(userRepository).findByUserId(anyString());
		doReturn(lwEntity2).when(locationWeatherRepository).findByUniqueId(anyString());
		LocationWeatherDTO dto = new ModelMapper().map(lwEntity2, LocationWeatherDTO.class);
		assertNotNull(dto);
		assertEquals(dto.isFavorite(),false);
		dto = weatherService.addToFavorites(lwEntity2.getUniqueId(), user2.getUserId());
		assertNotNull(dto);
		assertEquals(dto.isFavorite(),true);
		
	}
	
	
	@Test
	public void testRemoveFromFavorites()
	{
		doReturn(user2).when(userRepository).findByUserId(anyString());
		doReturn(lwEntity1).when(locationWeatherRepository).findByUniqueId(anyString());
		
		LocationWeatherDTO dto = new ModelMapper().map(lwEntity1, LocationWeatherDTO.class);
		assertNotNull(dto);
		assertEquals(dto.isFavorite(),true);
	    dto = weatherService.deleteFromFavorites(lwEntity1.getUniqueId(), user2.getUserId());
		assertNotNull(dto);
		assertEquals(dto.isFavorite(), false);
	}
	
}
