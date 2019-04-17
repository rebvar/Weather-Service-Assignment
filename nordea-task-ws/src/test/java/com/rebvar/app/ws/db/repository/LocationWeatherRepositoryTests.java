package com.rebvar.app.ws.db.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rebvar.app.ws.db.entity.AnonymForecastItemEntity;
import com.rebvar.app.ws.db.entity.AnonymLocationWeatherEntity;
import com.rebvar.app.ws.db.entity.ForecastItemEntity;
import com.rebvar.app.ws.db.entity.LocationWeatherEntity;
import com.rebvar.app.ws.db.entity.UserEntity;
import com.rebvar.app.ws.db.repository.UserRepository;
import com.rebvar.app.ws.weather_location_app.model.request.UserFavoritesRequestModel;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LocationWeatherRepositoryTests {

	@Autowired
	UserRepository userRepository;

	@Autowired
	LocationWeatherRepository locationWeatherRepository;

	@Autowired
	AnonymLocationWeatherRepository anonymLocationWeatherRepository;

	@Autowired
	LocationWeatherForecatRepository locationWeatherForecatRepository;

	@Autowired
	AnonymLocationWeatherForecatRepository anonymLocationWeatherForecatRepository;

	static boolean dataCreated = false;

	@BeforeEach
	void setUp() throws Exception {

		if (!dataCreated)
			createRecrods();
	}

	@Test
	final void testFindUserByName() {
		String name = "User1";
		List<UserEntity> users = userRepository.findAllByName(name);
		assertNotNull(users);
		assertTrue(users.size() == 1);
		UserEntity user = users.get(0);
		assertTrue(user.getName().equals(name));
	}

	@Test
	final void testFindUsersByPattern() {
		String pattern = "ser";
		List<UserEntity> users = userRepository.findUsersByPattern(pattern);
		assertNotNull(users);
		assertTrue(users.size() == 2);

		UserEntity user = users.get(0);
		assertTrue(user.getName().contains(pattern));
	}

	@Test
	final void testFindUserEntityByUserId() {
		String userId = "User1RandomId1";
		UserEntity userEntity = userRepository.findByUserId(userId);

		assertNotNull(userEntity);
		assertTrue(userEntity.getUserId().equals(userId));
	}
	
	@Test
	final void testUserLocationWeatherFindByUser() {
		String userId = "User1RandomId1";
		UserEntity userEntity = userRepository.findByUserId(userId);
		assertNotNull(userEntity);
		assertTrue(userEntity.getUserId().equals(userId));
		
		List<LocationWeatherEntity> lwe = locationWeatherRepository.findAllByUserDetails(userEntity);
		assertTrue(lwe.size()==1);
		assertTrue(lwe.get(0).getCity().compareTo("City1")==0);
		assertTrue (lwe.get(0).getTemprature()==1);
		userId = "User2RandomId2";
		
		userEntity = userRepository.findByUserId(userId);
		lwe = locationWeatherRepository.findAllByUserDetails(userEntity);
		assertNotNull(userEntity);
		assertTrue(userEntity.getUserId().equals(userId));
		
		assertTrue(lwe.size()==2);
		assertTrue(lwe.get(0).getCity().compareTo("City2")==0);
		assertTrue(lwe.get(1).getCity().compareTo("New York")==0);
		assertTrue(lwe.get(0).getTemprature()==260);
		assertTrue(lwe.get(1).getTemprature()==320);
		
	}
	
	@Test
	public void testCheckAnonymRepositoryFirstElement()
	{
		Iterable<AnonymLocationWeatherEntity> aIter = anonymLocationWeatherRepository.findAll();
		List<AnonymLocationWeatherEntity> aEntities= new ArrayList<AnonymLocationWeatherEntity>();
		aIter.forEach(it -> aEntities.add(it));
		assertTrue(aEntities.size()==1);
		assertTrue(aEntities.get(0).getCity().compareTo("New York")==0);
		assertTrue(aEntities.get(0).getDate().compareTo(new Date())<0);
	}
	
	@Test
	public void testCheckAnonymRepositoryFirstElementHasForecast()
	{
		Iterable<AnonymLocationWeatherEntity> aIter = anonymLocationWeatherRepository.findAll();
		List<AnonymLocationWeatherEntity> aEntities= new ArrayList<AnonymLocationWeatherEntity>();
		aIter.forEach(it -> aEntities.add(it));
		assertTrue(aEntities.size()==1);
		assertTrue(aEntities.get(0).getCity().compareTo("New York")==0);
		assertTrue(aEntities.get(0).getDate().compareTo(new Date())<0);
		
		
		Iterable<AnonymForecastItemEntity> frIter = anonymLocationWeatherForecatRepository.findAll();
		List<AnonymForecastItemEntity> frEntities= new ArrayList<>();
		frIter.forEach(it -> frEntities.add(it));
		assertTrue(frEntities.size()==1);		
		
		List<AnonymForecastItemEntity> forecasts = anonymLocationWeatherForecatRepository.findAllByAnonymLocationWeather(aEntities.get(0));
		
		assertTrue(forecasts.size()==1);
		assertTrue(forecasts.get(0).getTemprature() == 99.99);
		assertTrue(forecasts.get(0).getPressure() == 999);
	}
	
	

	private void createRecrods() {
		userRepository.deleteAll();
		locationWeatherRepository.deleteAll();
		locationWeatherForecatRepository.deleteAll();
		anonymLocationWeatherRepository.deleteAll();
		anonymLocationWeatherForecatRepository.deleteAll();

		UserEntity user1 = new UserEntity();
		user1.setName("User1");
		user1.setUserId("User1RandomId1");
		user1.setEncryptedPassword("User1Password");
		user1.setEmail("user1@company.com");

		LocationWeatherEntity lwEntity = new LocationWeatherEntity();
		lwEntity.setCity("City1");
		lwEntity.setLat(1);
		lwEntity.setLon(1);
		lwEntity.setCountryCode("FI");
		Date date = new Date(2000, 1, 1, 0, 0, 0);
		lwEntity.setDate(date);
		lwEntity.setTemprature(1);
		lwEntity.setExtra("");
		lwEntity.setLocationValid(true);
		lwEntity.setUniqueId("UniqueId1");
		lwEntity.setTimestamp(date.getTime());
		lwEntity.setUserDetails(user1);
		List<LocationWeatherEntity> U1lwEntites = new ArrayList<>();
		U1lwEntites.add(lwEntity);

		user1.setWeatherSearches(U1lwEntites);

		userRepository.save(user1);

		UserEntity user2 = new UserEntity();
		user2.setName("User2");
		user2.setUserId("User2RandomId2");
		user2.setEncryptedPassword("User2Password");
		user2.setEmail("user2@company.com");

		List<LocationWeatherEntity> U2lwEntites = new ArrayList<>();

		LocationWeatherEntity lwEntity1 = new LocationWeatherEntity();
		lwEntity1.setCity("City2");
		lwEntity1.setLat(100);
		lwEntity1.setLon(500);
		lwEntity1.setCountryCode("GB");
		date = new Date(2019, 1, 1, 0, 0, 0);
		lwEntity1.setDate(date);
		lwEntity1.setTemprature(260);
		lwEntity1.setExtra("");
		lwEntity1.setLocationValid(true);
		lwEntity1.setUniqueId("UniqueId4");
		lwEntity1.setTimestamp(date.getTime());
		lwEntity1.setUserDetails(user2);
		ForecastItemEntity forecastItem = new ForecastItemEntity();
		forecastItem.setHumidity(88);
		forecastItem.setPressure(888);
		forecastItem.setSkyCloud(88);
		forecastItem.setTemprature(88.88);
		forecastItem.setTimestamp(date.getTime());
		forecastItem.setWindDirection(88);
		forecastItem.setWindSpeed(88);
		forecastItem.setLocation_weather(lwEntity1);
		List<ForecastItemEntity> forecastEntities = new ArrayList<ForecastItemEntity>();
		forecastEntities.add(forecastItem);
		lwEntity1.setForecasts(forecastEntities);
		
		U2lwEntites.add(lwEntity1);

		LocationWeatherEntity lwEntity2 = new LocationWeatherEntity();
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

		userRepository.save(user2);

		AnonymLocationWeatherEntity anLwEntity = new AnonymLocationWeatherEntity();
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

		AnonymForecastItemEntity anforecastItem = new AnonymForecastItemEntity();
		anforecastItem.setHumidity(99);
		anforecastItem.setPressure(999);
		anforecastItem.setSkyCloud(99);
		anforecastItem.setTemprature(99.99);
		anforecastItem.setTimestamp(date.getTime());
		anforecastItem.setWindDirection(99);
		anforecastItem.setWindSpeed(99);
		anforecastItem.setAnonymLocationWeather(anLwEntity);
		List<AnonymForecastItemEntity> anForecastEntities = new ArrayList<AnonymForecastItemEntity>();
		anForecastEntities.add(anforecastItem);
		anLwEntity.setForecasts(anForecastEntities);
		anonymLocationWeatherRepository.save(anLwEntity);

		dataCreated = true;
	}
}
