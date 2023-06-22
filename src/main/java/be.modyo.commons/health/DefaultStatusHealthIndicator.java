package be.modyo.commons.health;

import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

@Slf4j
@AllArgsConstructor
public class DefaultStatusHealthIndicator extends AbstractHealthIndicator {

  private final Runnable healthStatusChecker;

  @Override
  protected void doHealthCheck(Health.Builder builder) {
    try {
      healthStatusChecker.run();
      builder.up();
    } catch (FeignException e) {
      log.warn("action=healthCheckFailure, error={}", e.getMessage());
      builder.withDetail("error", e.getMessage());
      builder.down();
    }
  }
}
