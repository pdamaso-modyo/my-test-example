package be.modyo.commons.handlers;

import be.modyo.commons.exceptions.TechnicalErrorException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultFeignErrorDecoder implements ErrorDecoder {

  @Setter
  private ErrorDecoder decoder;

  public DefaultFeignErrorDecoder() {
    this.decoder = new Default();
  }

  @Override
  public Exception decode(String methodKey, Response response) {
    Exception exception = decoder.decode(methodKey, response);
    log.error("action=decode, method={} error={}", methodKey, exception.getMessage());
    return new TechnicalErrorException(response.status(), methodKey, exception.getMessage(), exception);
  }
}
