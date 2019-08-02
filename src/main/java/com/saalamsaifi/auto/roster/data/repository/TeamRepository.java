package com.saalamsaifi.auto.roster.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.saalamsaifi.auto.roster.exception.InvalidTeamIdException;
import com.saalamsaifi.auto.roster.mongodb.collection.TeamCollection;

public interface TeamRepository extends MongoRepository<TeamCollection, String> {

  default TeamCollection updateById(TeamCollection team) {
    TeamCollection temp = findById(team.getId())
        .orElseThrow(() -> new InvalidTeamIdException(team.getId()));

    temp.setMaxWfrlAllowed(team.getMaxWfrlAllowed());
    temp.setName(team.getName());

    return save(temp);
  }

}
