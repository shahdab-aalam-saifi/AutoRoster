package com.saalamsaifi.auto.roster.data.repository;

import java.util.Optional;

import com.saalamsaifi.auto.roster.mongodb.collection.Collection;

public interface SearchRepository {
	/**
	 * @param groupId
	 * @return
	 */
	Optional<Collection> findByGroupId(String groupId);

	/**
	 * @param memberId
	 * @return
	 */
	Optional<Collection> findByMemberId(String memberId);
}
