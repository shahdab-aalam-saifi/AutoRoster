package com.saalamsaifi.auto.roster.exception;

public class InvalidTeamIdException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  
  private final String invalidId;
  
  public InvalidTeamIdException(String invalidId) {
    this.invalidId = invalidId;
  }
  
  public String getInvalidId() {
    return this.invalidId;
  }
}
