package com.saalamsaifi.auto.roster.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saalamsaifi.auto.roster.model.Group;
import com.saalamsaifi.auto.roster.model.Member;
import com.saalamsaifi.auto.roster.model.Team;
import com.saalamsaifi.auto.roster.service.IdentityService;
import com.saalamsaifi.auto.roster.service.SequenceService;

@Service
public class IdentityServiceImpl implements IdentityService {
	@Autowired
	private SequenceService sequence;

	@Override
	public void assignId(Team team) {
		if (team == null) {
			throw new IllegalArgumentException("team == null");
		}

		team.setId(sequence.getNextTeamSequence());

		if (team.getGroups() != null) {
			team.getGroups().stream().forEach(this::assignId);
		}
	}

	@Override
	public void assignId(Group group) {
		if (group == null) {
			throw new IllegalArgumentException("group == null");
		}

		group.setId(sequence.getNextGroupSequence());

		if (group.getMembers() != null) {
			group.getMembers().stream().forEach(this::assignId);
		}
	}

	@Override
	public void assignId(Member member) {
		if (member == null) {
			throw new IllegalArgumentException("member == null");
		}

		member.setId(sequence.getNextMemberSequence());
	}

}
