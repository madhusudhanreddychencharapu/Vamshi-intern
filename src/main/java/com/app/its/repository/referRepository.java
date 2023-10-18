package com.app.its.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;


import com.app.its.model.referDetails;

public interface referRepository extends MongoRepository<referDetails, Integer> {	
	 List<referDetails> findByUserId(String userId);
	 referDetails findByReferId(int id);
	 List<referDetails> findByStatus(String status);
}
