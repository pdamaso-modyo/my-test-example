package be.modyo.commons.exceptions;

public class TechnicalErrorException extends InternalErrorException {

  public TechnicalErrorException(int status, String code, String message) {
    super(status, code, message);
  }

  public TechnicalErrorException(int status, String code, String message, Throwable cause) {
    super(status, code, message, cause);
  }
}
