package com.saalamsaifi.auto.roster.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.saalamsaifi.auto.roster.exception.InvalidTeamIdException;
import com.saalamsaifi.auto.roster.mongodb.collection.GroupCollection;

public interface GroupRepository extends MongoRepository<GroupCollection, String> {

  default GroupCollection updateById(GroupCollection group) {
    GroupCollection temp = findById(group.getId())
        .orElseThrow(() -> new InvalidTeamIdException(group.getId()));

    temp.setName(group.getName());
    temp.setMaxWfrlAllowed(group.getMaxWfrlAllowed());

    return save(temp);
  }

}
