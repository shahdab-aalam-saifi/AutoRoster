package com.saalamsaifi.auto.roster.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
  private String message;

  private String url;

  private LocalDateTime time;

  ErrorResponse(String message, String url, LocalDateTime time) {
    this.message = message;
    this.url = url;
    this.time = time;
  }

  public static ErrorResponseBuilder builder() {
    return new ErrorResponseBuilder();
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setTime(LocalDateTime time) {
    this.time = time;
  }

  @Override
  public int hashCode() {
    return Objects.hash(message, time, url);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof ErrorResponse)) {
      return false;
    }
    ErrorResponse other = (ErrorResponse) obj;
    return Objects.equals(message, other.message) && Objects.equals(time, other.time)
        && Objects.equals(url, other.url);
  }

  public static class ErrorResponseBuilder {
    private String message;

    private String url;

    private LocalDateTime time;

    public ErrorResponseBuilder message(String message) {
      this.message = message;
      return this;
    }

    public ErrorResponseBuilder url(String url) {
      this.url = url;
      return this;
    }

    public ErrorResponseBuilder time(LocalDateTime time) {
      this.time = time;
      return this;
    }

    public ErrorResponse build() {
      return new ErrorResponse(this.message, this.url, this.time);
    }

    public String toString() {
      return "ErrorResponse.ErrorResponseBuilder(message=" + this.message + ", url=" + this.url
          + ", time=" + this.time + ")";
    }
  }

  public String getMessage() {
    return this.message;
  }

  public String getUrl() {
    return this.url;
  }

  public LocalDateTime getTime() {
    return this.time;
  }

  public String toString() {
    return "ErrorResponse(message=" + getMessage() + ", url=" + getUrl() + ", time=" + getTime()
        + ")";
  }
}
