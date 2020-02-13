package com.saalamsaifi.auto.roster.data.repository.impl;

import com.saalamsaifi.auto.roster.data.repository.SearchRepository;
import com.saalamsaifi.auto.roster.mongodb.collection.Collection;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.lang.Nullable;

public class SearchRepositoryImpl implements SearchRepository {
  @Autowired private MongoTemplate mongoTemplate;

  /** */
  @Override
  @Nullable
  public Optional<Collection> findByGroupId(String groupId) {
    Query query = new Query(Criteria.where("groups.groupId").is(groupId)).limit(1);

    Collection collection = mongoTemplate.findOne(query, Collection.class);

    return Optional.ofNullable(collection);
  }

  /** */
  @Override
  @Nullable
  public Optional<Collection> findByMemberId(String memberId) {
    Query query = new Query(Criteria.where("groups.members.memberId").is(memberId)).limit(1);

    Collection collection = mongoTemplate.findOne(query, Collection.class);

    return Optional.ofNullable(collection);
  }
}
