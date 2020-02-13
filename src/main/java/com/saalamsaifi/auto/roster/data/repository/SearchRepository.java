package com.saalamsaifi.auto.roster.data.repository;

import com.saalamsaifi.auto.roster.mongodb.collection.Collection;
import java.util.Optional;

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
