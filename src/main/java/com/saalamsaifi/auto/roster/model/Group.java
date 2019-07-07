package com.saalamsaifi.auto.roster.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString()
public class Group {
  @NotBlank
	private String teamId;
  
	private String id;
  @NotBlank
	private String name;
  @PositiveOrZero
  private int maxWfrlAllowed;
}
