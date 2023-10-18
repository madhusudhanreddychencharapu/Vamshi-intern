package com.app.its.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.app.its.model.userDetails;

public interface userRepository extends MongoRepository<userDetails, Integer> {
	userDetails findAllByUserName(String userName);
	userDetails findById(String id);
}
