package com.rebvar.app.ws.db.repository;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rebvar.app.ws.db.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
	UserEntity findByEmail(String email);
	UserEntity findByUserId(String id);
	List<UserEntity> findAllByName(String id);
	
	

	/**
	 * @param pattern
	 * @return Searches the table for partial name match. Custom query
	 */
	@Query(value="select * from User where name LIKE %:pattern%",nativeQuery=true)
	List<UserEntity> findUsersByPattern(@Param("pattern") String pattern);
	
}
