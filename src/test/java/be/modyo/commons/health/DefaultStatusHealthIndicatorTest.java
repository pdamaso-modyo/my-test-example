package be.modyo.commons.health;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;

import feign.FeignException.BadRequest;
import feign.FeignException.InternalServerError;
import feign.Request;
import feign.Request.Body;
import feign.Request.HttpMethod;
import java.util.Collection;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.actuate.health.Health;

class DefaultStatusHealthIndicatorTest {

  DefaultStatusHealthIndicator defaultStatusHealthIndicator;
  private Runnable healthCheckProcess;

  @BeforeEach
  void setUp() {
    healthCheckProcess = Mockito.mock(Runnable.class);
    defaultStatusHealthIndicator = new DefaultStatusHealthIndicator(healthCheckProcess);
  }

  @Test
  void testHealthCheckIsUp() {

    Health health = defaultStatusHealthIndicator.health();

    assertThat(health).isNotNull();
    assertThat(health.getStatus()).isNotNull();
    assertThat(health.getStatus().getCode()).isEqualTo("UP");
    assertThat(health.getStatus().getDescription()).isEmpty();

    then(healthCheckProcess).should().run();
  }

  @Test
  void testHealthCheckWhenFeignClientException() {

    Request request = getSomeRequest();
    doThrow(new BadRequest("some client error", request, "{}".getBytes(), Map.of()))
        .when(healthCheckProcess).run();

    Health health = defaultStatusHealthIndicator.health();

    assertThat(health).isNotNull();
    assertThat(health.getStatus()).isNotNull();
    assertThat(health.getStatus().getCode()).isEqualTo("DOWN");
    assertThat(health.getStatus().getDescription()).isEmpty();
    assertThat(health.getDetails()).hasFieldOrPropertyWithValue("error", "some client error");
  }

  @Test
  void testHealthCheckWhenFeignServerException() {

    Request request = getSomeRequest();
    doThrow(new InternalServerError("some server error", request, "{}".getBytes(), Map.of()))
        .when(healthCheckProcess).run();

    Health health = defaultStatusHealthIndicator.health();

    assertThat(health).isNotNull();
    assertThat(health.getStatus()).isNotNull();
    assertThat(health.getStatus().getCode()).isEqualTo("DOWN");
    assertThat(health.getStatus().getDescription()).isEmpty();
    assertThat(health.getDetails()).hasFieldOrPropertyWithValue("error", "some server error");
  }

  @Test
  void testHealthCheckWhenRuntimeException() {

    doThrow(new RuntimeException("runtime error"))
        .when(healthCheckProcess).run();

    Health health = defaultStatusHealthIndicator.health();

    assertThat(health).isNotNull();
    assertThat(health.getStatus()).isNotNull();
    assertThat(health.getStatus().getCode()).isEqualTo("DOWN");
    assertThat(health.getStatus().getDescription()).isEmpty();
  }

  private static Request getSomeRequest() {
    Map<String, Collection<String>> headers = Map.of();
    return Request.create(
        HttpMethod.GET,
        "url",
        headers,
        Body.create("{}".getBytes()),
        null
    );
  }
}
