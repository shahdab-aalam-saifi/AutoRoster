package com.saalamsaifi.auto.roster.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
@JsonInclude(value = Include.NON_NULL)
public class ErrorResponse {
  private String message;
  private String url;
  private LocalDateTime time;
}
