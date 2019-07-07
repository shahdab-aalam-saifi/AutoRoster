package com.saalamsaifi.auto.roster.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PathMapping {

//  TEAM
	public static final String URL_ADD_NEW_TEAM = "/add/";
	public static final String URL_UPDATE_TEAM_BY_ID =	"/update/id";
	public static final String URL_GET_TEAM_BY_ID = "/get/id";
	public static final String URL_GET_ALL_TEAM = "/get/";
	
//	GROUP
  public static final String URL_ADD_NEW_GROUP = "/add/";
  public static final String URL_UPDATE_GROUP_BY_ID =  "/update/id";
  public static final String URL_GET_GROUP_BY_ID = "/get/id";
  public static final String URL_GET_GROUP_TEAM = "/get/";
}
