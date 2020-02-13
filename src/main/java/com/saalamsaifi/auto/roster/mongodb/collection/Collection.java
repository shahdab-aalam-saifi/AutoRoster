package com.saalamsaifi.auto.roster.mongodb.collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.saalamsaifi.auto.roster.model.Group;
import java.util.List;
import java.util.Objects;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("team")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Collection {
  @Field("teamId")
  private String id;

  private String name;

  private int maxWfrlAllowed;

  private List<Group> groups;

  Collection(String id, String name, int maxWfrlAllowed, List<Group> groups) {
    this.id = id;
    this.name = name;
    this.maxWfrlAllowed = maxWfrlAllowed;
    this.groups = groups;
  }

  public static CollectionBuilder builder() {
    return new CollectionBuilder();
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

  @Override
  public int hashCode() {
    return Objects.hash(groups, id, maxWfrlAllowed, name);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Collection)) {
      return false;
    }
    Collection other = (Collection) obj;
    return Objects.equals(groups, other.groups) && Objects.equals(id, other.id)
        && maxWfrlAllowed == other.maxWfrlAllowed && Objects.equals(name, other.name);
  }

  public String toString() {
    return "Collection(id=" + getId() + ", name=" + getName() + ", maxWfrlAllowed="
        + getMaxWfrlAllowed() + ", groups=" + getGroups() + ")";
  }

  public static class CollectionBuilder {
    private String id;

    private String name;

    private int maxWfrlAllowed;

    private List<Group> groups;

    public CollectionBuilder id(String id) {
      this.id = id;
      return this;
    }

    public CollectionBuilder name(String name) {
      this.name = name;
      return this;
    }

    public CollectionBuilder maxWfrlAllowed(int maxWfrlAllowed) {
      this.maxWfrlAllowed = maxWfrlAllowed;
      return this;
    }

    public CollectionBuilder groups(List<Group> groups) {
      this.groups = groups;
      return this;
    }

    public Collection build() {
      return new Collection(this.id, this.name, this.maxWfrlAllowed, this.groups);
    }

    public String toString() {
      return "Collection.CollectionBuilder(id=" + this.id + ", name=" + this.name
          + ", maxWfrlAllowed=" + this.maxWfrlAllowed + ", groups=" + this.groups + ")";
    }
  }
}
