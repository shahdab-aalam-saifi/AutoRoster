package com.saalamsaifi.auto.roster.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectConstant {
	// Project
	public static final String BASE_PACKAGE = "com.saalamsaifi.auto.roster";
	public static final String PROP_USER_HOME = "user.home";
	public static final String SEPARATOR = "::";
	public static final String WFRL = "WFRL";
	public static final String NO_WFRL = "";


	// Sequence
	public static final String TEAM_SEQUENCE = "team_sequence";
	public static final String GROUP_SEQUENCE = "group_sequence";
	public static final String MEMBER_SEQUENCE = "member_sequence";

	// Id's prefix
	public static final String ID_PREFIX_TEAM = "T";
	public static final String ID_PREFIX_GROUP = "G";
	public static final String ID_PREFIX_MEMBER = "M";
	
}
