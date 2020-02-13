package com.saalamsaifi.auto.roster.controller;

import static com.saalamsaifi.auto.roster.constant.PathMapping.URL_ADD_NEW_TEAM;
import static com.saalamsaifi.auto.roster.constant.PathMapping.URL_GET_TEAM;
import static com.saalamsaifi.auto.roster.constant.PathMapping.URL_UPDATE_TEAM_BY_ID;
import static com.saalamsaifi.auto.roster.constant.RequestParameter.TEAM_ID;

import com.saalamsaifi.auto.roster.data.repository.TeamRepository;
import com.saalamsaifi.auto.roster.model.Team;
import com.saalamsaifi.auto.roster.mongodb.collection.Collection;
import com.saalamsaifi.auto.roster.service.IdentityService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamController {
  @Autowired private TeamRepository repository;

  @Autowired private IdentityService identityService;

  /**
   * @param team
   * @return
   */
  @PostMapping(
      path = {URL_ADD_NEW_TEAM},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<Collection> add(@RequestBody @Valid Team team) {
    identityService.assignId(team);

    Collection collection =
        Collection.builder()
            .id(team.getId())
            .name(team.getName())
            .maxWfrlAllowed(team.getMaxWfrlAllowed())
            .groups(team.getGroups())
            .build();

    return ResponseEntity.ok(repository.save(collection));
  }

  /**
   * @param id
   * @param team
   * @return
   */
  @PostMapping(
      path = {URL_UPDATE_TEAM_BY_ID},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<Collection> update(
      @RequestParam(required = true, name = TEAM_ID) String id, @RequestBody @Valid Team team) {
    Collection collection = repository.findById(id).orElse(null);

    if (collection == null) {
      return ResponseEntity.notFound().build();
    }

    collection.setName(team.getName());
    collection.setMaxWfrlAllowed(team.getMaxWfrlAllowed());

    if (team.getGroups() != null) {
      team.getGroups().stream().forEach(group -> identityService.assignId(group));
    }

    collection.setGroups(team.getGroups());

    return ResponseEntity.ok(repository.save(collection));
  }

  /** @return */
  @GetMapping(
      path = {URL_GET_TEAM},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<List<Collection>> get() {
    return ResponseEntity.ok().body(repository.findAll());
  }

  /**
   * @param id
   * @return
   */
  @GetMapping(
      path = {URL_GET_TEAM},
      produces = {MediaType.APPLICATION_JSON_VALUE},
      params = {TEAM_ID})
  public ResponseEntity<Collection> get(@RequestParam(required = true, name = TEAM_ID) String id) {
    if (id == null || id.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }

    Collection collection = repository.findById(id).orElse(null);

    if (collection != null) {
      return ResponseEntity.ok().body(collection);
    }
    return ResponseEntity.notFound().build();
  }
}
