package com.saalamsaifi.auto.roster.mongodb.collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.saalamsaifi.auto.roster.model.Group;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(value = "team")
@Data
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Collection {
  @Field(value = "teamId")
  private String id;

  private String name;

  private int maxWfrlAllowed;

  private List<Group> groups;
}
