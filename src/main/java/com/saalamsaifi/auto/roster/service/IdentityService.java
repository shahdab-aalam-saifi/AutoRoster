package com.saalamsaifi.auto.roster.service;

import com.saalamsaifi.auto.roster.model.Group;
import com.saalamsaifi.auto.roster.model.Member;
import com.saalamsaifi.auto.roster.model.Team;
import org.springframework.stereotype.Service;

@Service
public interface IdentityService {
  /** @param team */
  void assignId(Team team);

  /** @param group */
  void assignId(Group group);

  /** @param member */
  void assignId(Member member);
}
