package com.smis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smis.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long>{

	Users findByUserName(String username);
	
	
	@Query("select Max(c.userId) from Users c ")
	Long findMaxSerial();
}
