package com.saalamsaifi.auto.roster.service;

import org.springframework.stereotype.Service;

@Service
public interface SequenceService {
	/**
	 * @return
	 */
	String getNextTeamSequence();

	/**
	 * @return
	 */
	String getNextGroupSequence();

	/**
	 * @return
	 */
	String getNextMemberSequence();
}
