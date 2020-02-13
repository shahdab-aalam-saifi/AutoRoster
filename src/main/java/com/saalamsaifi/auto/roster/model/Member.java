package com.saalamsaifi.auto.roster.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.DayOfWeek;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@ToString
@JsonInclude(value = Include.NON_NULL)
public class Member {
  @Field(value = "memberId")
  private String id;

  @NotBlank private String name;

  private boolean isInterested;
  private List<DayOfWeek> likes;
  private List<DayOfWeek> dislikes;
}
