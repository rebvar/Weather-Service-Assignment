package com.rebvar.app.ws.db.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rebvar.app.ws.db.entity.AnonymForecastItemEntity;
import com.rebvar.app.ws.db.entity.AnonymLocationWeatherEntity;

@Repository
public interface AnonymLocationWeatherForecatRepository extends CrudRepository<AnonymForecastItemEntity, Long> {
	
	public List< AnonymForecastItemEntity > findAllByAnonymLocationWeather(AnonymLocationWeatherEntity anonym_location_weather);
	
}
