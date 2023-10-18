package com.app.its.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.app.its.model.jobDetails;


@Repository
public interface jobRepository extends MongoRepository<jobDetails, Integer> {
	List<jobDetails> findByDescription(String Description);
	jobDetails findByJobId(int id);
}
