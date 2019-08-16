package com.saalamsaifi.auto.roster.controller;

import static com.saalamsaifi.auto.roster.constant.PathMapping.URL_ADD_NEW_MEMBER;
import static com.saalamsaifi.auto.roster.constant.PathMapping.URL_GET_GROUP;
import static com.saalamsaifi.auto.roster.constant.PathMapping.URL_UPDATE_MEMBER_BY_ID;
import static com.saalamsaifi.auto.roster.constant.RequestParameter.GROUP_ID;
import static com.saalamsaifi.auto.roster.constant.RequestParameter.MEMBER_ID;
import static com.saalamsaifi.auto.roster.constant.RequestParameter.TEAM_ID;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.saalamsaifi.auto.roster.model.Member;
import com.saalamsaifi.auto.roster.mongodb.collection.Collection;

@RestController
public class MemberController {
	@Autowired
	private TeamRepository repository;

	/**
	 * @param teamId
	 * @param groupId
	 * @param member
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, path = { URL_ADD_NEW_MEMBER }, produces = {
			MediaType.APPLICATION_JSON_VALUE }, params = { TEAM_ID, GROUP_ID })
	public ResponseEntity<Collection> add(@RequestParam(required = true, name = TEAM_ID) String teamId,
			@RequestParam(required = true, name = GROUP_ID) String groupId, @RequestBody @Valid Member member) {
		Collection collection = repository.findById(teamId).orElseThrow(null);

		if (collection.getGroups() != null) {
			List<Group> list = collection.getGroups().stream().filter(g -> g.getId().equals(groupId)).collect(Collectors
					.toList());

			if (list != null && !list.isEmpty()) {
				member.setId(ObjectId.get().toHexString());

				List<Member> members = list.get(0).getMembers();

				if (members != null && !members.isEmpty()) {
					members.add(member);
				} else {
					List<Member> temp = new ArrayList<>();
					temp.add(member);
				}

				return ResponseEntity.ok().body(repository.save(collection));
			} else {
				return ResponseEntity.badRequest().build();
			}
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	/**
	 * @param teamId
	 * @param groupId
	 * @param member
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, path = { URL_UPDATE_MEMBER_BY_ID }, produces = {
			MediaType.APPLICATION_JSON_VALUE }, params = { TEAM_ID, GROUP_ID, MEMBER_ID })
	public ResponseEntity<Collection> update(@RequestParam(required = true, name = TEAM_ID) String teamId,
			@RequestParam(required = true, name = GROUP_ID) String groupId,
			@RequestParam(required = true, name = MEMBER_ID) String memberId, @RequestBody @Valid Member member) {
		Collection collection = repository.findById(teamId).orElseThrow(null);

		if (collection.getGroups() != null) {
			List<Group> list = collection.getGroups().stream().filter(g -> g.getId().equals(groupId)).collect(Collectors
					.toList());

			if (list != null && !list.isEmpty()) {
				List<Member> members = list.get(0).getMembers();

				if (members != null) {
					List<Member> memberList = members.stream().filter(m -> m.getId().equals(memberId)).collect(
							Collectors.toList());

					if (memberList != null && !memberList.isEmpty()) {
						Member temp = memberList.get(0);

						temp.setName(member.getName());
						temp.setInterested(member.isInterested());
						temp.setLikes(member.getLikes());
						temp.setDislikes(member.getDislikes());

						return ResponseEntity.ok().body(repository.save(collection));
					} else {
						return ResponseEntity.badRequest().build();
					}
				} else {
					return ResponseEntity.badRequest().build();
				}
			} else {
				return ResponseEntity.badRequest().build();
			}
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	@RequestMapping(method = { RequestMethod.GET }, path = { URL_GET_GROUP }, produces = {
			MediaType.APPLICATION_JSON_VALUE }, params = { TEAM_ID, GROUP_ID, MEMBER_ID })
	public ResponseEntity<Group> get(@RequestParam(required = true, name = TEAM_ID) String teamId,
			@RequestParam(required = true, name = GROUP_ID) String groupId,
			@RequestParam(required = true, name = MEMBER_ID) String memberId) {
		Collection collection = repository.findById(teamId).orElseThrow(null);

		if (collection.getGroups() == null) {
			return ResponseEntity.ok().body(null);
		} else {
			List<Group> list = collection.getGroups().stream().filter(g -> g.getId().equals(groupId)).collect(Collectors
					.toList());
			if (list != null && !list.isEmpty()) {
				return ResponseEntity.ok().body(list.get(0));
			} else {
				return ResponseEntity.badRequest().build();
			}
		}
	}

}
