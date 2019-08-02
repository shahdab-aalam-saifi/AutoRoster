package com.saalamsaifi.auto.roster.mongodb.collection;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(value = "member")
public class MemberCollection {
  private String id;
  private String name;
  private boolean isInterested;
  private List<String> likes;
  private List<String> dislikes;
}
