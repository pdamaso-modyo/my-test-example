package be.modyo.commons.exceptions;

public class BusinessErrorException extends InternalErrorException {

  public BusinessErrorException(int status, String code, String message) {
    super(status, code, message);
  }

}
