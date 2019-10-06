package com.saalamsaifi.auto.roster.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class InvalidTeamIdException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final String invalidId;
}
