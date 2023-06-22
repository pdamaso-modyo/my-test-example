package be.modyo.commons.health;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.actuate.health.HealthIndicator;

@Getter
@AllArgsConstructor(staticName = "of")
public class IntegrationHealthIndicator {

  private final String service;
  private final HealthIndicator healthIndicator;
}
