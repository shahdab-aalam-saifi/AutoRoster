package com.saalamsaifi.auto.roster.data.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.saalamsaifi.auto.roster.mongodb.collection.Collection;

public interface TeamRepository extends MongoRepository<Collection, String> {
	@Query(value = "{'groups.groupId': ?0}")
	List<Collection> findByGroupId(String groupId);
}
