package com.saalamsaifi.auto.roster.model;

import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import org.springframework.data.mongodb.core.mapping.Field;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Group {
  @Field("groupId")
  private String id;

  @NotBlank
  private String name;

  @PositiveOrZero
  private int maxWfrlAllowed;

  @Valid
  private List<Member> members;

  Group(String id, String name, int maxWfrlAllowed, List<Member> members) {
    this.id = id;
    this.name = name;
    this.maxWfrlAllowed = maxWfrlAllowed;
    this.members = members;
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

  public void setMembers(List<Member> members) {
    this.members = members;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, maxWfrlAllowed, members, name);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Group)) {
      return false;
    }
    Group other = (Group) obj;
    return Objects.equals(id, other.id) && maxWfrlAllowed == other.maxWfrlAllowed
        && Objects.equals(members, other.members) && Objects.equals(name, other.name);
  }

  public static GroupBuilder builder() {
    return new GroupBuilder();
  }

  public static class GroupBuilder {
    private String id;

    private String name;

    private int maxWfrlAllowed;

    private List<Member> members;

    public GroupBuilder id(String id) {
      this.id = id;
      return this;
    }

    public GroupBuilder name(String name) {
      this.name = name;
      return this;
    }

    public GroupBuilder maxWfrlAllowed(int maxWfrlAllowed) {
      this.maxWfrlAllowed = maxWfrlAllowed;
      return this;
    }

    public GroupBuilder members(List<Member> members) {
      this.members = members;
      return this;
    }

    public Group build() {
      return new Group(this.id, this.name, this.maxWfrlAllowed, this.members);
    }

    public String toString() {
      return "Group.GroupBuilder(id=" + this.id + ", name=" + this.name + ", maxWfrlAllowed="
          + this.maxWfrlAllowed + ", members=" + this.members + ")";
    }
  }

  public String toString() {
    return "Group(id=" + getId() + ", name=" + getName() + ", maxWfrlAllowed=" + getMaxWfrlAllowed()
        + ", members=" + getMembers() + ")";
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

  public List<Member> getMembers() {
    return this.members;
  }
}
