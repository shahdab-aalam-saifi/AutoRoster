package com.saalamsaifi.auto.roster.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.saalamsaifi.auto.roster.constant.PathMapping;
import com.saalamsaifi.auto.roster.data.repository.GroupRepository;
import com.saalamsaifi.auto.roster.data.repository.MemberRepository;
import com.saalamsaifi.auto.roster.data.repository.TeamRepository;
import com.saalamsaifi.auto.roster.model.Group;
import com.saalamsaifi.auto.roster.model.Member;
import com.saalamsaifi.auto.roster.model.Team;
import com.saalamsaifi.auto.roster.mongodb.collection.GroupCollection;
import com.saalamsaifi.auto.roster.mongodb.collection.MemberCollection;
import com.saalamsaifi.auto.roster.mongodb.collection.TeamCollection;

@RestController
public class RequestController {
  @Autowired
  private TeamRepository teamRepository;

  @Autowired
  private GroupRepository groupRepository;

  @Autowired
  private MemberRepository memberRepository;

  /**
   * @param member
   * @return
   */
  private MemberCollection save(Member member) {
    MemberCollection collection = MemberCollection
        .builder()
        .name(member.getName())
        .likes(member.getLikes())
        .dislikes(member.getDislikes())
        .isInterested(member.isInterested())
        .build();

    return memberRepository.save(collection);
  }

  /**
   * @param group
   * @param memberCollections
   * @return
   */
  private GroupCollection save(Group group, List<MemberCollection> memberCollections) {
    GroupCollection collection = GroupCollection
        .builder()
        .name(group.getName())
        .maxWfrlAllowed(group.getMaxWfrlAllowed())
        .members(memberCollections)
        .build();

    return groupRepository.save(collection);
  }

  /**
   * @param team
   * @param groupCollections
   * @return
   */
  private TeamCollection save(Team team, List<GroupCollection> groupCollections) {
    TeamCollection collection = TeamCollection
        .builder()
        .name(team.getName())
        .maxWfrlAllowed(team.getMaxWfrlAllowed())
        .groups(groupCollections)
        .build();

    return teamRepository.save(collection);
  }

  @PutMapping(path = { PathMapping.URL_ADD_NEW_TEAM })
  public ResponseEntity<TeamCollection> addNewTeam(@RequestBody @Valid Team team) {
    List<GroupCollection> groups = new ArrayList<>();

    team.getGroups().forEach(group -> {
      List<MemberCollection> members = new ArrayList<>();

      group.getMembers().forEach(member -> {
        members.add(save(member));
      });

      groups.add(save(group, members));
    });

    return ResponseEntity.ok(save(team, groups));
  }
}
