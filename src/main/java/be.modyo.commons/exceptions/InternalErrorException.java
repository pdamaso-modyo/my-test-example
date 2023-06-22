package be.modyo.commons.exceptions;

import lombok.Getter;

@Getter
public abstract class InternalErrorException extends RuntimeException {

  private final int status;
  private final String code;

  InternalErrorException(int status, String code, String message) {
    super(message);
    this.status = status;
    this.code = code;
  }

  InternalErrorException(int status, String code, String message, Throwable cause) {
    super(message, cause);
    this.status = status;
    this.code = code;
  }

}
