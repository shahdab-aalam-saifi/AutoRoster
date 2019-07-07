package com.saalamsaifi.auto.roster.model;

import java.util.List;

import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class Member {
	private String id;
	private String name;
	private boolean isInterested;
	private List<String> likes;
	private List<String> dislikes;
}
