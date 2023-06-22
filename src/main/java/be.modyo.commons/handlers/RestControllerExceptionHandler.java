package be.modyo.commons.handlers;

import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;

import be.modyo.commons.exceptions.BusinessErrorException;
import be.modyo.commons.exceptions.TechnicalErrorException;
import jakarta.annotation.Priority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(BusinessErrorException.class)
  public ResponseEntity<Object> handle(BusinessErrorException ex, WebRequest request) {
    log.error("handleBusinessError, exception={}, class={}, request={}",
        ex.getMessage(), ex.getClass().getName(), request);
    HttpStatusCode httpStatus = HttpStatusCode.valueOf(ex.getStatus());
    return handleInternal(ex, request, httpStatus, ex.getCode());
  }

  @ExceptionHandler(TechnicalErrorException.class)
  public ResponseEntity<Object> handle(TechnicalErrorException ex, WebRequest request) {
    log.error("handleTechnicalError, exception={}, class={}, request={}",
        ex.getMessage(), ex.getClass().getName(), request);
    HttpStatus httpStatus = HttpStatus.valueOf(ex.getStatus());
    return handleInternal(ex, request, httpStatus, ex.getCode());
  }

  @ExceptionHandler(Exception.class)
  @Priority(value = LOWEST_PRECEDENCE)
  public ResponseEntity<Object> handle(Exception ex, WebRequest request) {
    log.error("handleException, exception={}, class={}, request={}",
        ex.getMessage(), ex.getClass().getName(), request);
    HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    return handleInternal(ex, request, httpStatus, ".");
  }

  private ResponseEntity<Object> handleInternal(
      Exception exception,
      WebRequest request,
      HttpStatusCode httpStatus,
      String code) {

    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, exception.getMessage());
    problemDetail.setProperty("source", code);

    return handleExceptionInternal(
        exception, problemDetail, new HttpHeaders(), httpStatus, request
    );
  }
}
