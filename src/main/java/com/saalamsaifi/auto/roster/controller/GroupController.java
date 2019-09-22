package com.saalamsaifi.auto.roster.controller;

import static com.saalamsaifi.auto.roster.constant.PathMapping.URL_ADD_NEW_GROUP;
import static com.saalamsaifi.auto.roster.constant.PathMapping.URL_GET_GROUP;
import static com.saalamsaifi.auto.roster.constant.PathMapping.URL_UPDATE_GROUP_BY_ID;
import static com.saalamsaifi.auto.roster.constant.RequestParameter.GROUP_ID;
import static com.saalamsaifi.auto.roster.constant.RequestParameter.TEAM_ID;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saalamsaifi.auto.roster.data.repository.TeamRepository;
import com.saalamsaifi.auto.roster.model.Group;
import com.saalamsaifi.auto.roster.mongodb.collection.Collection;
import com.saalamsaifi.auto.roster.service.IdentityService;

@RestController
public class GroupController {
	@Autowired
	private TeamRepository repository;

	@Autowired
	private IdentityService identityService;

	/**
	 * @param teamId
	 * @param group
	 * @return
	 */
	@PostMapping(path = { URL_ADD_NEW_GROUP }, produces = { MediaType.APPLICATION_JSON_VALUE }, params = { TEAM_ID })
	public ResponseEntity<List<Group>> add(@RequestParam(required = true, name = TEAM_ID) String teamId,
			@RequestBody @Valid Group group) {
		Collection collection = repository.findById(teamId).orElse(null);

		if (collection == null) {
			return ResponseEntity.notFound().build();
		}

		identityService.assignId(group);

		List<Group> list = collection.getGroups();

		if (list == null) {
			List<Group> groups = new ArrayList<>();
			groups.add(group);
			collection.setGroups(groups);
		} else {
			collection.getGroups().add(group);
		}

		return ResponseEntity.ok().body(repository.save(collection).getGroups());
	}

	/**
	 * @param teamId
	 * @param groupId
	 * @param group
	 * @return
	 */
	@PostMapping(path = { URL_UPDATE_GROUP_BY_ID }, produces = { MediaType.APPLICATION_JSON_VALUE }, params = { TEAM_ID,
			GROUP_ID })
	public ResponseEntity<List<Group>> update(@RequestParam(required = true, name = GROUP_ID) String groupId,
			@RequestBody @Valid Group group) {
		Collection collection = repository.findByGroupId(groupId).orElse(null);

		if (collection == null) {
			return ResponseEntity.notFound().build();
		}

		List<Group> list = collection.getGroups().stream().filter(g -> g.getId().equals(groupId))
				.collect(Collectors.toList());

		list.stream().findFirst().ifPresent(g -> {
			g.setName(group.getName());
			g.setMaxWfrlAllowed(group.getMaxWfrlAllowed());

			if (group.getMembers() != null) {
				group.getMembers().forEach(member -> identityService.assignId(member));
				g.setMembers(group.getMembers());
			}
		});

		return ResponseEntity.ok().body(repository.save(collection).getGroups());
	}

	/**
	 * @param teamId
	 * @return
	 */
	@GetMapping(path = { URL_GET_GROUP }, produces = { MediaType.APPLICATION_JSON_VALUE }, params = { TEAM_ID })
	public ResponseEntity<List<Group>> getByTeamId(@RequestParam(required = true, name = TEAM_ID) String teamId) {
		Collection collection = repository.findById(teamId).orElse(null);

		if (collection == null) {
			return ResponseEntity.badRequest().build();
		}

		if (collection.getGroups() == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok().body(collection.getGroups());
		}
	}

	/**
	 * @param groupId
	 * @return
	 */
	@GetMapping(path = { URL_GET_GROUP }, produces = { MediaType.APPLICATION_JSON_VALUE }, params = { GROUP_ID })
	public ResponseEntity<Group> getByGroupId(@RequestParam(required = true, name = GROUP_ID) String groupId) {
		Collection collection = repository.findByGroupId(groupId).orElse(null);

		if (collection == null) {
			return ResponseEntity.notFound().build();
		}

		List<Group> list = collection.getGroups().stream().filter(g -> g.getId().equals(groupId))
				.collect(Collectors.toList());

		return ResponseEntity.ok().body(list.get(0));
	}
}
