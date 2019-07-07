package com.saalamsaifi.auto.roster.mongodb.collection;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Document(value = "team")
@Data
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeamCollection {
	@Id
	private String id;
	@Indexed(unique = true)
	private String name;
	private int maxWfrlAllowed;
	@DBRef
	private List<GroupCollection> groups;
}
