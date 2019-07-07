package com.saalamsaifi.auto.roster.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saalamsaifi.auto.roster.constant.PathMapping;
import com.saalamsaifi.auto.roster.data.repository.GroupRepository;
import com.saalamsaifi.auto.roster.data.repository.TeamRepository;
import com.saalamsaifi.auto.roster.exception.InvalidTeamIdException;
import com.saalamsaifi.auto.roster.model.Group;
import com.saalamsaifi.auto.roster.mongodb.collection.GroupCollection;
import com.saalamsaifi.auto.roster.mongodb.collection.TeamCollection;

@RestController
@RequestMapping(path = "group")
public class GroupController {

  @Autowired
  private TeamRepository teamRepository;

  @Autowired
  private GroupRepository groupRepository;

  @PutMapping(path = { PathMapping.URL_ADD_NEW_GROUP })
  public ResponseEntity<TeamCollection> add(@RequestBody @Valid Group group) {
    TeamCollection collection = teamRepository
        .findById(group.getTeamId())
        .orElseThrow(() -> new InvalidTeamIdException(group.getTeamId()));

    GroupCollection groupCollection = groupRepository
        .save(
            GroupCollection
                .builder()
                .name(group.getName())
                .maxWfrlAllowed(group.getMaxWfrlAllowed())
                .build());

    List<GroupCollection> list = collection.getGroups();
    if (list == null) {
      List<GroupCollection> groups = new ArrayList<GroupCollection>();
      groups.add(groupCollection);
      collection.setGroups(groups);
    } else {
      list.add(groupCollection);
    }

    teamRepository.save(collection);

    return ResponseEntity.ok(collection);
  }

}
