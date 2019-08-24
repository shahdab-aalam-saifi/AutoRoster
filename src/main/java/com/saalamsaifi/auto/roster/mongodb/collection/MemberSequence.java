package com.saalamsaifi.auto.roster.mongodb.collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.ToString;

@Document(value = "member_sequence")
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberSequence {
	@Id
	private String id;
	private String sequence;
}
