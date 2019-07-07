package com.saalamsaifi.auto.roster.model;

import java.util.List;

import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class MemberRule {
	private String id;
	private boolean isApplicable;
	private List<String> likes;
	private List<String> dislikes;
}
