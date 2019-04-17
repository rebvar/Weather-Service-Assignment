package com.rebvar.app.ws.db.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name="user")
public class UserEntity extends AbstractUserEntity implements Serializable {
	
	private static final long serialVersionUID = 8225342489296017278L;

	@Id
	@GeneratedValue
	private long id;
	
	
	/**
	 *	Link to the location weather table, user searches. 
	 */
	@OneToMany(mappedBy = "userDetails", cascade = CascadeType.ALL)
	private List<LocationWeatherEntity> weatherSearches;
	
	
	public void setWeatherSearches(List<LocationWeatherEntity> weatherSearches) {
		this.weatherSearches = weatherSearches;
	}

	public List<LocationWeatherEntity> getWeatherSearches() {
		return weatherSearches;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
