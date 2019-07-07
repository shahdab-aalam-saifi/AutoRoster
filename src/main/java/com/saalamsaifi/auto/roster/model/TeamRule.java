package com.saalamsaifi.auto.roster.model;

import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class TeamRule {
	private String id;
	private boolean isApplicable;
	private int maxWfrlAllowed;
}
