package be.modyo.commons.handlers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import be.modyo.commons.exceptions.TechnicalErrorException;
import feign.Request;
import feign.Request.Body;
import feign.Request.HttpMethod;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestClientException;

class DefaultFeignErrorDecoderTest {

  private final ObjectMapper objectMapper = new ObjectMapper();
  private final ErrorDecoder decoder = Mockito.mock(ErrorDecoder.class);

  DefaultFeignErrorDecoder defaultErrorDecoder;

  @BeforeEach
  void setUp() {
    defaultErrorDecoder = new DefaultFeignErrorDecoder();
  }

  @Test
  void decodeClientError() throws JsonProcessingException {

    int status = 400;
    Response response = buildResponse(status, "bad request");

    Exception exception = defaultErrorDecoder.decode("method", response);

    assertThat(exception)
        .isInstanceOf(TechnicalErrorException.class)
        .hasMessageContaining("[400] during [GET] to [url] [method]");
  }

  @Test
  void decodeServerError() throws JsonProcessingException {

    int status = 500;
    Response response = buildResponse(status, "internal server error");

    Exception exception = defaultErrorDecoder.decode("method", response);

    assertThat(exception)
        .isInstanceOf(TechnicalErrorException.class)
        .hasMessageContaining("[500] during [GET] to [url] [method]");
  }

  @Test
  void decodeStatus1xx() throws JsonProcessingException {

    defaultErrorDecoder.setDecoder(decoder);

    int status = 100;
    Response response = buildResponse(status, "some message");

    given(decoder.decode("method", response))
        .willReturn(new RestClientException("rest error"));

    Exception exception = defaultErrorDecoder.decode("method", response);

    assertThat(exception)
        .isInstanceOf(TechnicalErrorException.class)
        .hasMessage("rest error");
  }

  @Test
  void decodeWhenNoBody() throws IOException {

    int status = 500;
    String message = "no body available";
    Response response = buildMockResponse(status, message);

    Exception exception = defaultErrorDecoder.decode("method", response);

    assertThat(exception)
        .isInstanceOf(TechnicalErrorException.class)
        .hasMessage("[500] during [GET] to [url] [method]: []");
  }

  private Response buildMockResponse(int status, String message) throws IOException {

    Map<String, Collection<String>> headers = Map.of();
    Request request = Request.create(
        HttpMethod.GET,
        "url",
        headers,
        Body.create("[]".getBytes()),
        null
    );
    Response.Body responseBody = mock(Response.Body.class);
    given(responseBody.asInputStream()).willThrow(new IOException(message));
    return Response.builder()
        .request(request)
        .body(responseBody)
        .status(status)
        .build();
  }

  private Response buildResponse(int status, String message) throws JsonProcessingException {

    byte[] responseBody = objectMapper.writeValueAsBytes(message);

    Map<String, Collection<String>> headers = Map.of();
    Request request = Request.create(
        HttpMethod.GET,
        "url",
        headers,
        null,
        Charset.defaultCharset(),
        null
    );
    return Response.builder()
        .request(request)
        .body(responseBody)
        .status(status)
        .build();
  }
}
