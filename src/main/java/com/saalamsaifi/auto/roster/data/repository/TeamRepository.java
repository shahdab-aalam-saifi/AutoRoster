package com.saalamsaifi.auto.roster.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.saalamsaifi.auto.roster.mongodb.collection.Collection;

public interface TeamRepository extends MongoRepository<Collection, String> {
}
