package com.rebvar.app.ws.db.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rebvar.app.ws.db.entity.LocationWeatherEntity;
import com.rebvar.app.ws.db.entity.UserEntity;

@Repository
public interface LocationWeatherRepository extends CrudRepository<LocationWeatherEntity, Long> {
	
	public LocationWeatherEntity findByUniqueId(String uniqueId);
	public List<LocationWeatherEntity> findAllByUserDetails(UserEntity user);
	/**
	 * @param user
	 * @param isFavorite
	 * 
	 * 
	 * @return Searches by two fields and returns a list of favorites=isFavorite.
	 * 			meaning that is either returns the favorites or not favorites.
	 */
	public List<LocationWeatherEntity> findAllByUserDetailsAndIsFavorite(UserEntity user, boolean isFavorite);
}
