package com.saalamsaifi.auto.roster.controller;

import static com.saalamsaifi.auto.roster.constant.PathMapping.URL_ADD_NEW_MEMBER;
import static com.saalamsaifi.auto.roster.constant.PathMapping.URL_GET_GROUP;
import static com.saalamsaifi.auto.roster.constant.PathMapping.URL_UPDATE_MEMBER_BY_ID;
import static com.saalamsaifi.auto.roster.constant.RequestParameter.GROUP_ID;
import static com.saalamsaifi.auto.roster.constant.RequestParameter.MEMBER_ID;

import com.saalamsaifi.auto.roster.data.repository.TeamRepository;
import com.saalamsaifi.auto.roster.model.Group;
import com.saalamsaifi.auto.roster.model.Member;
import com.saalamsaifi.auto.roster.mongodb.collection.Collection;
import com.saalamsaifi.auto.roster.service.IdentityService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public class MemberController {
  @Autowired private TeamRepository repository;

  @Autowired private IdentityService identityService;

  /**
   * @param teamId
   * @param groupId
   * @param member
   * @return
   */
  @PostMapping(
      path = {URL_ADD_NEW_MEMBER},
      produces = {MediaType.APPLICATION_JSON_VALUE},
      params = {GROUP_ID})
  public ResponseEntity<Collection> add(
      @RequestParam(required = true, name = GROUP_ID) String groupId,
      @RequestBody @Valid Member member) {

    Collection collection = repository.findByGroupId(groupId).orElse(null);

    if (collection != null) {
      List<Group> list =
          collection.getGroups().stream()
              .filter(g -> g.getId().equals(groupId))
              .collect(Collectors.toList());

      identityService.assignId(member);

      Group group = list.get(0);

      List<Member> members = group.getMembers();

      if (members != null && !members.isEmpty()) {
        members.add(member);
      } else {
        List<Member> temp = new ArrayList<>();
        temp.add(member);

        group.setMembers(temp);
      }

      return ResponseEntity.ok().body(repository.save(collection));
    } else {
      return ResponseEntity.badRequest().build();
    }
  }

  /**
   * @param collection
   * @param memberId
   * @return
   */
  @Nullable
  private Group findGroupByMemberId(Collection collection, String memberId) {
    List<Group> groups =
        collection.getGroups().stream()
            .filter(
                group ->
                    group.getMembers().stream()
                            .filter(member -> member.getId().equals(memberId))
                            .count()
                        > 0)
            .collect(Collectors.toList());

    return !groups.isEmpty() ? groups.get(0) : null;
  }

  /**
   * @param memberId
   * @param member
   * @return
   */
  @PostMapping(
      path = {URL_UPDATE_MEMBER_BY_ID},
      produces = {MediaType.APPLICATION_JSON_VALUE},
      params = {MEMBER_ID})
  public ResponseEntity<Collection> update(
      @RequestParam(required = true, name = MEMBER_ID) String memberId,
      @RequestBody @Valid Member member) {
    Collection collection = repository.findByMemberId(memberId).orElse(null);

    if (collection != null) {
      Group group = findGroupByMemberId(collection, memberId);

      if (group != null) {
        List<Member> members = group.getMembers();

        if (members != null) {
          List<Member> memberList =
              members.stream().filter(m -> m.getId().equals(memberId)).collect(Collectors.toList());

          if (!memberList.isEmpty()) {
            Member temp = memberList.get(0);

            temp.setName(member.getName());
            temp.setInterested(member.isInterested());
            temp.setLikes(member.getLikes());
            temp.setDislikes(member.getDislikes());

            return ResponseEntity.ok().body(repository.save(collection));
          }
        }
      }
    }
    return ResponseEntity.badRequest().build();
  }

  @GetMapping(
      path = {URL_GET_GROUP},
      produces = {MediaType.APPLICATION_JSON_VALUE},
      params = {MEMBER_ID})
  public ResponseEntity<Member> get(
      @RequestParam(required = true, name = MEMBER_ID) String memberId) {
    Collection collection = repository.findByMemberId(memberId).orElse(null);

    if (collection != null) {
      Group group = findGroupByMemberId(collection, memberId);

      if (group != null) {
        List<Member> members = group.getMembers();

        if (members != null) {
          List<Member> memberList =
              members.stream().filter(m -> m.getId().equals(memberId)).collect(Collectors.toList());

          return ResponseEntity.ok().body(memberList.get(0));
        }
      }
    }
    return ResponseEntity.badRequest().build();
  }
}
