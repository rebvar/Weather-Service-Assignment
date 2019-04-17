package com.rebvar.app.ws.db.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rebvar.app.ws.db.entity.AnonymLocationWeatherEntity;

@Repository
public interface AnonymLocationWeatherRepository extends CrudRepository<AnonymLocationWeatherEntity, Long> {
}
