package com.saalamsaifi.auto.roster.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PathMapping {
  private static final String TEAM = "/team";
  private static final String GROUP = "/group";
  private static final String ADD = "/add";
  private static final String UPDATE_BY_ID = "/update/id";
  private static final String GET_BY_ID = "/get/id";
  private static final String GET = "/get";

// TEAM
  public static final String URL_ADD_NEW_TEAM = TEAM + ADD;
  public static final String URL_UPDATE_TEAM_BY_ID = TEAM + UPDATE_BY_ID;
  public static final String URL_GET_TEAM_BY_ID = TEAM + GET_BY_ID;
  public static final String URL_GET_ALL_TEAM = TEAM + GET;

// GROUP
  public static final String URL_ADD_NEW_GROUP = GROUP + ADD;
  public static final String URL_UPDATE_GROUP_BY_ID = GROUP + UPDATE_BY_ID;
  public static final String URL_GET_GROUP_BY_ID = GROUP + GET_BY_ID;
  public static final String URL_GET_GROUP_TEAM = GROUP + GET;
}
