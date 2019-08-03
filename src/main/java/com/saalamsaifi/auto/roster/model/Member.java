package com.saalamsaifi.auto.roster.model;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
  private String id;

  @NotBlank
  private String name;

  private boolean isInterested;
  private List<String> likes;
  private List<String> dislikes;
}
