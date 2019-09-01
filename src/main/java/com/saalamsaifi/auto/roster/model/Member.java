package com.saalamsaifi.auto.roster.model;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
@JsonInclude(value = Include.NON_NULL)

public class Member {
	@Field(value = "memberId")
	private String id;

	@NotBlank
	@Indexed(unique = true)
	private String name;

	private boolean isInterested;
	private List<String> likes;
	private List<String> dislikes;
}
