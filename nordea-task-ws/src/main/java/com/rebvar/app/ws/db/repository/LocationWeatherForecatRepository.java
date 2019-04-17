package com.rebvar.app.ws.db.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.rebvar.app.ws.db.entity.ForecastItemEntity;

@Repository
public interface LocationWeatherForecatRepository extends CrudRepository<ForecastItemEntity, Long> {
	
	
	
}
