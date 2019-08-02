package com.saalamsaifi.auto.roster.controller;

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
import com.saalamsaifi.auto.roster.data.repository.TeamRepository;
import com.saalamsaifi.auto.roster.model.Team;
import com.saalamsaifi.auto.roster.mongodb.collection.GroupCollection;
import com.saalamsaifi.auto.roster.mongodb.collection.TeamCollection;

@RestController
@RequestMapping(path = { "/team" })
public class TeamController {

  @Autowired
  private TeamRepository teamRepository;

  @PutMapping(path = { PathMapping.URL_ADD_NEW_TEAM })
  public ResponseEntity<TeamCollection> add(@RequestBody @Valid Team team) {
    TeamCollection collection = teamRepository
        .save(
            TeamCollection
                .builder()
                .name(team.getName())
                .maxWfrlAllowed(team.getMaxWfrlAllowed())
                .build());
    
    return ResponseEntity.ok(collection);
  }

  @GetMapping(path = { PathMapping.URL_GET_TEAM_BY_ID })
  public ResponseEntity<TeamCollection> getById(@RequestParam(required = true) String teamId) {
    return ResponseEntity.ok(teamRepository.findById(teamId).orElse(null));
  }

  @PostMapping(path = { PathMapping.URL_UPDATE_TEAM_BY_ID })
  public ResponseEntity<TeamCollection> updateById(@Valid @RequestBody Team team) {
    TeamCollection collection = teamRepository
        .updateById(
            TeamCollection
                .builder()
                .id(team.getId())
                .name(team.getName())
                .maxWfrlAllowed(team.getMaxWfrlAllowed())
                .build());
    return ResponseEntity.accepted().body(collection);
  }

  @GetMapping(path = { PathMapping.URL_GET_ALL_TEAM })
  public ResponseEntity<List<TeamCollection>> get() {
    return ResponseEntity.ok(teamRepository.findAll());
  }

}
