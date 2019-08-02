package com.saalamsaifi.auto.roster.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    TeamCollection teamCollection = findTeamById(group.getTeamId());

    GroupCollection groupCollection = groupRepository
        .save(
            GroupCollection
                .builder()
                .name(group.getName())
                .maxWfrlAllowed(group.getMaxWfrlAllowed())
                .build());

    List<GroupCollection> list = teamCollection.getGroups();

    if (list == null) {
      List<GroupCollection> groups = new ArrayList<GroupCollection>();
      groups.add(groupCollection);
      teamCollection.setGroups(groups);
    } else {
      list.add(groupCollection);
    }

    teamRepository.save(teamCollection);

    return ResponseEntity.ok(teamCollection);
  }

  @GetMapping(path = { PathMapping.URL_GET_GROUP_BY_ID })
  public ResponseEntity<GroupCollection> getById(@RequestParam(required = true) String teamId,
      @RequestParam(required = true) String groupId) {

    GroupCollection groupCollection = findTeamById(teamId)
        .getGroups()
        .stream()
        .filter(group -> group.getId().equals(groupId))
        .findFirst()
        .orElse(null);

    return ResponseEntity.ok(groupCollection);
  }

  @GetMapping(path = { PathMapping.URL_GET_GROUP_TEAM })
  public ResponseEntity<List<GroupCollection>> getByTeamId(@RequestParam(
      required = true) String teamId) {
    return ResponseEntity.ok(findTeamById(teamId).getGroups());
  }

  @PostMapping(path = { PathMapping.URL_UPDATE_GROUP_BY_ID })
  public ResponseEntity<GroupCollection> updateById(@Valid @RequestBody Group group) {
    GroupCollection groupCollection = findTeamById(group.getTeamId())
        .getGroups()
        .stream()
        .filter(g -> g.getId().equals(group.getId()))
        .findFirst()
        .orElseThrow(() -> new InvalidTeamIdException(group.getId()));

    groupRepository
        .updateById(
            GroupCollection
                .builder()
                .id(group.getId())
                .name(group.getName())
                .maxWfrlAllowed(group.getMaxWfrlAllowed())
                .build());

    return null;
  }

  private TeamCollection findTeamById(String teamId) {
    return teamRepository.findById(teamId).orElseThrow(() -> new InvalidTeamIdException(teamId));

  }
}
