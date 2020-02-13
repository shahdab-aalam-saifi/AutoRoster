package com.saalamsaifi.auto.roster.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import org.springframework.data.mongodb.core.mapping.Field;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Member {
  @Field("memberId")
  private String id;

  @NotBlank
  private String name;

  private boolean isInterested;

  private List<DayOfWeek> likes;

  private List<DayOfWeek> dislikes;

  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setInterested(boolean isInterested) {
    this.isInterested = isInterested;
  }

  public void setLikes(List<DayOfWeek> likes) {
    this.likes = likes;
  }

  public void setDislikes(List<DayOfWeek> dislikes) {
    this.dislikes = dislikes;
  }

  @Override
  public int hashCode() {
    return Objects.hash(dislikes, id, isInterested, likes, name);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Member)) {
      return false;
    }
    Member other = (Member) obj;
    return Objects.equals(dislikes, other.dislikes) && Objects.equals(id, other.id)
        && isInterested == other.isInterested && Objects.equals(likes, other.likes)
        && Objects.equals(name, other.name);
  }

  Member(String id, String name, boolean isInterested, List<DayOfWeek> likes,
      List<DayOfWeek> dislikes) {
    this.id = id;
    this.name = name;
    this.isInterested = isInterested;
    this.likes = likes;
    this.dislikes = dislikes;
  }

  public static MemberBuilder builder() {
    return new MemberBuilder();
  }

  public static class MemberBuilder {
    private String id;

    private String name;

    private boolean isInterested;

    private List<DayOfWeek> likes;

    private List<DayOfWeek> dislikes;

    public MemberBuilder id(String id) {
      this.id = id;
      return this;
    }

    public MemberBuilder name(String name) {
      this.name = name;
      return this;
    }

    public MemberBuilder isInterested(boolean isInterested) {
      this.isInterested = isInterested;
      return this;
    }

    public MemberBuilder likes(List<DayOfWeek> likes) {
      this.likes = likes;
      return this;
    }

    public MemberBuilder dislikes(List<DayOfWeek> dislikes) {
      this.dislikes = dislikes;
      return this;
    }

    public Member build() {
      return new Member(this.id, this.name, this.isInterested, this.likes, this.dislikes);
    }

    public String toString() {
      return "Member.MemberBuilder(id=" + this.id + ", name=" + this.name + ", isInterested="
          + this.isInterested + ", likes=" + this.likes + ", dislikes=" + this.dislikes + ")";
    }
  }

  public String toString() {
    return "Member(id=" + getId() + ", name=" + getName() + ", isInterested=" + isInterested()
        + ", likes=" + getLikes() + ", dislikes=" + getDislikes() + ")";
  }

  public String getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public boolean isInterested() {
    return this.isInterested;
  }

  public List<DayOfWeek> getLikes() {
    return this.likes;
  }

  public List<DayOfWeek> getDislikes() {
    return this.dislikes;
  }
}
