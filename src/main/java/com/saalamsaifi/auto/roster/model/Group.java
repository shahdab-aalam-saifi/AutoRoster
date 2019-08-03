package com.saalamsaifi.auto.roster.model;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
@JsonInclude(value = Include.NON_NULL)
public class Group {
    private String id;

    @NotBlank
    private String name;

    @PositiveOrZero
    private int maxWfrlAllowed;

    @Valid
    private List<Member> members;
}
