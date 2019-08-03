package com.saalamsaifi.auto.roster.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PathMapping {
    private static final String TEAM = "/team";
    private static final String GROUP = "/group";
    private static final String ADD = "/add";
    private static final String UPDATE = "/update";
    private static final String GET = "/get";

// TEAM
    public static final String URL_ADD_NEW_TEAM = TEAM + ADD;
    public static final String URL_UPDATE_TEAM_BY_ID = TEAM + UPDATE;
    public static final String URL_GET_TEAM = TEAM + GET;

// GROUP
    public static final String URL_ADD_NEW_GROUP = GROUP + ADD;
    public static final String URL_UPDATE_GROUP_BY_ID = GROUP + UPDATE;
    public static final String URL_GET_GROUP = GROUP + GET;
}
