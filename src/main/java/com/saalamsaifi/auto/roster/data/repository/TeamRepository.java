package com.saalamsaifi.auto.roster.data.repository;

import com.saalamsaifi.auto.roster.mongodb.collection.Collection;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeamRepository extends MongoRepository<Collection, String>, SearchRepository {}
