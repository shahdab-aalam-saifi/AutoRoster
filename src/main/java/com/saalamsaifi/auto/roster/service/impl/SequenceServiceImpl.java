package com.saalamsaifi.auto.roster.service.impl;

import static com.saalamsaifi.auto.roster.constant.ProjectConstant.GROUP_SEQUENCE;
import static com.saalamsaifi.auto.roster.constant.ProjectConstant.ID_PREFIX_GROUP;
import static com.saalamsaifi.auto.roster.constant.ProjectConstant.ID_PREFIX_MEMBER;
import static com.saalamsaifi.auto.roster.constant.ProjectConstant.ID_PREFIX_TEAM;
import static com.saalamsaifi.auto.roster.constant.ProjectConstant.MEMBER_SEQUENCE;
import static com.saalamsaifi.auto.roster.constant.ProjectConstant.TEAM_SEQUENCE;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.saalamsaifi.auto.roster.mongodb.collection.GroupSequence;
import com.saalamsaifi.auto.roster.mongodb.collection.MemberSequence;
import com.saalamsaifi.auto.roster.mongodb.collection.TeamSequence;
import com.saalamsaifi.auto.roster.service.SequenceService;

@Service
public class SequenceServiceImpl implements SequenceService {
	@Autowired
	private MongoOperations operations;

	private Object getCounter(String sequenceName, Class<? extends Object> _class) {
		return operations.findAndModify(query(where("_id").is(sequenceName)), new Update().inc("sequence", 00001),
				options().returnNew(true).upsert(true), _class);
	}

	private String format(String sequence) {
		return String.format("%05d", Long.parseLong(sequence));
	}

	@Override
	public String getNextTeamSequence() {
		TeamSequence counter = (TeamSequence) getCounter(TEAM_SEQUENCE, TeamSequence.class);
		return ID_PREFIX_TEAM + format(counter.getSequence());
	}

	@Override
	public String getNextGroupSequence() {
		GroupSequence counter = (GroupSequence) getCounter(GROUP_SEQUENCE, GroupSequence.class);
		return ID_PREFIX_GROUP + format(counter.getSequence());
	}

	@Override
	public String getNextMemberSequence() {
		MemberSequence counter = (MemberSequence) getCounter(MEMBER_SEQUENCE, MemberSequence.class);
		return ID_PREFIX_MEMBER + format(counter.getSequence());
	}

}
