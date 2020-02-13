package com.saalamsaifi.auto.roster.exception.handler;

import com.saalamsaifi.auto.roster.exception.InvalidTeamIdException;
import com.saalamsaifi.auto.roster.response.ErrorResponse;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class TeamExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {InvalidTeamIdException.class})
  public final ResponseEntity<ErrorResponse> handleInvalidTeamIdException(
      InvalidTeamIdException exception, WebRequest request) {
    return new ResponseEntity<>(
        ErrorResponse.builder()
            .time(LocalDateTime.now())
            .message(MessageFormat.format("id: {0}", exception.getInvalidId()))
            .build(),
        HttpStatus.NOT_FOUND);
  }
}
