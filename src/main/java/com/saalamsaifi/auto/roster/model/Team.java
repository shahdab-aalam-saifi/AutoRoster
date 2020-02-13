package com.saalamsaifi.auto.roster.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Team {
  private String id;

  @NotBlank private String name;

  @PositiveOrZero private int maxWfrlAllowed;

  @Valid private List<Group> groups;

  Team(String id, String name, int maxWfrlAllowed, List<Group> groups) {
    this.id = id;
    this.name = name;
    this.maxWfrlAllowed = maxWfrlAllowed;
    this.groups = groups;
  }

  public static TeamBuilder builder() {
    return new TeamBuilder();
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setMaxWfrlAllowed(int maxWfrlAllowed) {
    this.maxWfrlAllowed = maxWfrlAllowed;
  }

  public void setGroups(List<Group> groups) {
    this.groups = groups;
  }

  public static class TeamBuilder {
    private String id;

    private String name;

    private int maxWfrlAllowed;

    private List<Group> groups;

    public TeamBuilder id(String id) {
      this.id = id;
      return this;
    }

    public TeamBuilder name(String name) {
      this.name = name;
      return this;
    }

    public TeamBuilder maxWfrlAllowed(int maxWfrlAllowed) {
      this.maxWfrlAllowed = maxWfrlAllowed;
      return this;
    }

    public TeamBuilder groups(List<Group> groups) {
      this.groups = groups;
      return this;
    }

    public Team build() {
      return new Team(this.id, this.name, this.maxWfrlAllowed, this.groups);
    }

    public String toString() {
      return "Team.TeamBuilder(id="
          + this.id
          + ", name="
          + this.name
          + ", maxWfrlAllowed="
          + this.maxWfrlAllowed
          + ", groups="
          + this.groups
          + ")";
    }
  }

  public String toString() {
    return "Team(id="
        + getId()
        + ", name="
        + getName()
        + ", maxWfrlAllowed="
        + getMaxWfrlAllowed()
        + ", groups="
        + getGroups()
        + ")";
  }

  public String getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public int getMaxWfrlAllowed() {
    return this.maxWfrlAllowed;
  }

  public List<Group> getGroups() {
    return this.groups;
  }
}
