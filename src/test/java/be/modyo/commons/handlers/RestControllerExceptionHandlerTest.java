package be.modyo.commons.handlers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.modyo.commons.exceptions.BusinessErrorException;
import be.modyo.commons.exceptions.TechnicalErrorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;

class RestControllerExceptionHandlerTest {

  RestControllerExceptionHandler restControllerExceptionHandler;

  @BeforeEach
  void setUp() {
    restControllerExceptionHandler = new RestControllerExceptionHandler();
  }

  @Test
  void handleBusinessErrorException() {

    ServletWebRequest webRequest = new ServletWebRequest(new MockHttpServletRequest());
    BusinessErrorException exception = new BusinessErrorException(500, "code", "message");

    ResponseEntity<Object> responseEntity = restControllerExceptionHandler.handle(exception, webRequest);

    assertThat(responseEntity).isNotNull();
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Test
  void handleTechnicalErrorExceptionWhen4xx() {

    ServletWebRequest webRequest = new ServletWebRequest(new MockHttpServletRequest());
    TechnicalErrorException exception = new TechnicalErrorException(400, "ERR-001", "message");

    ResponseEntity<Object> responseEntity = restControllerExceptionHandler.handle(exception, webRequest);

    assertThat(responseEntity).isNotNull();
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  void handleTechnicalErrorExceptionWhen5xx() {

    ServletWebRequest webRequest = new ServletWebRequest(new MockHttpServletRequest());
    TechnicalErrorException exception = new TechnicalErrorException(500, "ERR-001", "message");

    ResponseEntity<Object> responseEntity = restControllerExceptionHandler.handle(exception, webRequest);

    assertThat(responseEntity).isNotNull();
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Test
  void handleExceptionWithSpringMvc() throws Exception {

    ServletWebRequest webRequest = new ServletWebRequest(new MockHttpServletRequest());
    Exception exception = new HttpMessageNotWritableException("message");

    ResponseEntity<Object> responseEntity = restControllerExceptionHandler.handleException(exception, webRequest);

    assertThat(responseEntity).isNotNull();
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Test
  void handleExceptionWhenNoExceptionMapped() {

    ServletWebRequest webRequest = new ServletWebRequest(new MockHttpServletRequest());
    Exception exception = new RuntimeException("message");

    Exception rethrownException = assertThrows(Exception.class,
        () -> restControllerExceptionHandler.handleException(exception, webRequest));

    assertThat(rethrownException).isNotNull().isEqualTo(exception);
  }

  @Test
  void handleExceptionAsLastChance() {

    ServletWebRequest webRequest = new ServletWebRequest(new MockHttpServletRequest());
    Exception exception = new RuntimeException("message");

    ResponseEntity<Object> responseEntity = restControllerExceptionHandler.handle(exception, webRequest);

    assertThat(responseEntity).isNotNull();
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
