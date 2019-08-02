package com.saalamsaifi.auto.roster.model;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Member {
  private String id;

  @NotBlank
  @Size(min = 4, message = "Member name must not be less than 4 characters")
  private String name;

  private boolean isInterested;
  private List<String> likes;
  private List<String> dislikes;
}
