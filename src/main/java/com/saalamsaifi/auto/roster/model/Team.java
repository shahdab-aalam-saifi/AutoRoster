package com.saalamsaifi.auto.roster.model;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Team {
	private String id;
	
	@NotBlank
	@Size(min = 4, message = "Team name must not be less than 4 characters")
	private String name;
	
	@PositiveOrZero(message = "maxWfrlAllowed cannot be negative")
	private int maxWfrlAllowed;
	
	@Valid
	private List<Group> groups;
}
