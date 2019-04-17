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

import com.rebvar.app.ws.db.entity.LocationWeatherEntity;
import com.rebvar.app.ws.db.entity.UserEntity;
import com.rebvar.app.ws.db.repository.UserRepository;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepositoryTests {

	@Autowired
	UserRepository userRepository;
	
	static boolean dataCreated = false;
	

	@BeforeEach
	void setUp() throws Exception {
		
		if(!dataCreated) createRecrods();
	}
	
	@Test 
	final void testFindUserByName()
	{
		String name="User1";
		List<UserEntity> users = userRepository.findAllByName(name);
		assertNotNull(users);
		assertTrue(users.size() == 1);
		UserEntity user = users.get(0);
		assertTrue(user.getName().equals(name));
	}
	
	@Test 
	final void testFindUsersByPattern()
	{
		String pattern="ser";
		List<UserEntity> users = userRepository.findUsersByPattern(pattern);
		assertNotNull(users);
		assertTrue(users.size() == 2);
		
		UserEntity user = users.get(0);
		assertTrue(
				user.getName().contains(pattern)
				);
	}
		
	
	@Test 
	final void testFindUserEntityByUserId()
	{
		String userId = "User1RandomId1";
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		assertNotNull(userEntity);
		assertTrue(userEntity.getUserId().equals(userId));
	}
	
	private void createRecrods()
	{
		
		userRepository.deleteAll();
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
	     Date date=new Date(2000,1,1,0,0,0);
	     lwEntity.setDate(date);
	     lwEntity.setTemprature(1);
	     lwEntity.setExtra("");
	     lwEntity.setLocationValid(true);
	     lwEntity.setUniqueId("UniqueId1");
	     lwEntity.setTimestamp(date.getTime());
	     

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
	     date=new Date(2019,1,1,0,0,0);
	     lwEntity1.setDate(date);
	     lwEntity1.setTemprature(260);
	     lwEntity1.setExtra("");
	     lwEntity1.setLocationValid(true);
	     lwEntity1.setUniqueId("UniqueId4");
	     lwEntity1.setTimestamp(date.getTime());
	     
	     U2lwEntites.add(lwEntity1);
	     
	     LocationWeatherEntity lwEntity2 = new LocationWeatherEntity();
	     lwEntity2.setCity("New York");
	     lwEntity2.setLat(10);
	     lwEntity2.setLon(20);
	     lwEntity2.setCountryCode("US");
	     date=new Date(2010,1,1,0,0,0);
	     lwEntity2.setDate(date);
	     lwEntity2.setTemprature(320);
	     lwEntity2.setExtra("");
	     lwEntity2.setLocationValid(true);
	     lwEntity2.setUniqueId("UniqueId3");
	     lwEntity2.setTimestamp(date.getTime());
	     
	     U2lwEntites.add(lwEntity2);
	     
	     user2.setWeatherSearches(U2lwEntites);
	     
	     userRepository.save(user2);
	     
	     dataCreated = true;
	}
}


