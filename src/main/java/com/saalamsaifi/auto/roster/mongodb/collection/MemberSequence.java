package com.saalamsaifi.auto.roster.mongodb.collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("member_sequence")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberSequence {
  @Id
  private String id;

  private String sequence;

  public String getId() {
    return this.id;
  }

  public String getSequence() {
    return this.sequence;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setSequence(String sequence) {
    this.sequence = sequence;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, sequence);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof MemberSequence)) {
      return false;
    }
    MemberSequence other = (MemberSequence) obj;
    return Objects.equals(id, other.id) && Objects.equals(sequence, other.sequence);
  }

  public String toString() {
    return "MemberSequence(id=" + getId() + ", sequence=" + getSequence() + ")";
  }
}
