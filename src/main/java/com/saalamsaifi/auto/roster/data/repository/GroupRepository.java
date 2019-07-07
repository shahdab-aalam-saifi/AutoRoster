package com.saalamsaifi.auto.roster.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.saalamsaifi.auto.roster.mongodb.collection.GroupCollection;

public interface GroupRepository extends MongoRepository<GroupCollection, String> {

}
