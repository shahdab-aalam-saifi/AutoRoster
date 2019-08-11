package com.saalamsaifi.auto.roster.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.saalamsaifi.auto.roster.constant.PathMapping.*;
import com.saalamsaifi.auto.roster.data.repository.TeamRepository;
import com.saalamsaifi.auto.roster.model.Team;
import com.saalamsaifi.auto.roster.mongodb.collection.Collection;
import com.saalamsaifi.auto.roster.utils.Utils;

@RestController
public class TeamController {
    @Autowired
    private TeamRepository repository;

    /**
     * @param team
     * @return
     */
    @RequestMapping(
            method = { RequestMethod.POST, RequestMethod.PUT },
            path = { URL_ADD_NEW_TEAM },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Collection> add(@RequestBody @Valid Team team) {
        if (team.getGroups() != null) {
            team.getGroups().stream().forEach((group) -> {
                group.setId(Utils.getObjectId());

                if (group.getMembers() != null) {
                    group.getMembers().forEach((member) -> {
                        member.setId(Utils.getObjectId());
                    });
                }
            });
        }

        Collection collection = Collection.builder()
                .id(Utils.getObjectId())
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
    @RequestMapping(
            method = { RequestMethod.POST },
            path = { URL_UPDATE_TEAM_BY_ID },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Collection> update(@RequestParam(required = true, name = "id") String id,
            @RequestBody @Valid Team team) {
        Collection collection = repository.findById(id).orElseThrow(null);

        collection.setName(team.getName());
        collection.setMaxWfrlAllowed(team.getMaxWfrlAllowed());
        
        if (team.getGroups() != null) {
            team.getGroups().stream().forEach((group) -> {
                group.setId(Utils.getObjectId());

                if (group.getMembers() != null) {
                    group.getMembers().forEach((member) -> {
                        member.setId(Utils.getObjectId());
                    });
                }
            });
        }

        collection.setGroups(team.getGroups());

        return ResponseEntity.ok(repository.save(collection));
    }

    @RequestMapping(
            method = { RequestMethod.GET },
            path = { URL_GET_TEAM },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> get() {
        List<JSONObject> list = new ArrayList<>();
        repository.findAll().stream().forEach((team) -> {
            JSONObject json = new JSONObject();
            json.put("id", team.getId());
            json.put("name", team.getName());
            json.put("maxWfrlAllowed", team.getMaxWfrlAllowed());

            list.add(json);
        });
        JSONArray array = new JSONArray(list);
        return ResponseEntity.ok(array.toString());
    }

    @RequestMapping(
            method = { RequestMethod.GET },
            path = { URL_GET_TEAM },
            produces = { MediaType.APPLICATION_JSON_VALUE },
            params = { "id" })
    public ResponseEntity<Collection> get(@RequestParam(required = true, name = "id") String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }

        Collection collection = repository.findById(id).orElseThrow(null);

        return ResponseEntity.ok(collection);
    }

}
