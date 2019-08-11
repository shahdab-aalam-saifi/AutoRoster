package com.saalamsaifi.auto.roster.controller;

import static com.saalamsaifi.auto.roster.constant.PathMapping.URL_ADD_NEW_GROUP;
import static com.saalamsaifi.auto.roster.constant.PathMapping.URL_GET_GROUP;
import static com.saalamsaifi.auto.roster.constant.RequestParameter.TEAM_ID;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saalamsaifi.auto.roster.data.repository.TeamRepository;
import com.saalamsaifi.auto.roster.model.Group;
import com.saalamsaifi.auto.roster.mongodb.collection.Collection;

@RestController
public class GroupController {
    @Autowired
    private TeamRepository repository;

    @RequestMapping(
            method = { RequestMethod.POST, RequestMethod.PUT },
            path = { URL_ADD_NEW_GROUP },
            produces = { MediaType.APPLICATION_JSON_VALUE },
            params = { TEAM_ID })
    public ResponseEntity<Collection> add(
            @RequestParam(required = true, name = TEAM_ID) String teamId,
            @RequestBody @Valid Group group) {
        Collection collection = repository.findById(teamId).orElseThrow(null);

        group.setId(ObjectId.get().toHexString());

        if (group.getMembers() != null) {
            group.getMembers().forEach((member) -> {
                member.setId(ObjectId.get().toHexString());
            });
        }

        List<Group> list = collection.getGroups();

        if (list.isEmpty()) {
            List<Group> groups = new ArrayList<>();
            groups.add(group);
            collection.setGroups(groups);
        } else {
            collection.getGroups().add(group);
        }

        return ResponseEntity.ok(collection);
    }

    @RequestMapping(
            method = { RequestMethod.GET },
            path = { URL_GET_GROUP },
            produces = { MediaType.APPLICATION_JSON_VALUE },
            params = { TEAM_ID })
    public ResponseEntity<Object> get(
            @RequestParam(required = true, name = TEAM_ID) String teamId) {
        return null;
    }
}
