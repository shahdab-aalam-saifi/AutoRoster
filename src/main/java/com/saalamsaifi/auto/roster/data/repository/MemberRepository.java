package com.saalamsaifi.auto.roster.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.saalamsaifi.auto.roster.mongodb.collection.MemberCollection;

public interface MemberRepository extends MongoRepository<MemberCollection, String> {
}
