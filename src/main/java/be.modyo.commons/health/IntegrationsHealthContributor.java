package be.modyo.commons.health;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.boot.actuate.health.CompositeHealthContributor;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.NamedContributor;

public class IntegrationsHealthContributor implements CompositeHealthContributor {

  private final Map<String, HealthContributor> contributors = new LinkedHashMap<>();

  public IntegrationsHealthContributor(List<IntegrationHealthIndicator> healthIndicators) {
    healthIndicators.forEach(healthIndicator ->
        contributors.put(healthIndicator.getService(), healthIndicator.getHealthIndicator()));
  }

  @Override
  public HealthContributor getContributor(String name) {
    return contributors.get(name);
  }

  @Override
  public Iterator<NamedContributor<HealthContributor>> iterator() {
    return contributors.entrySet().stream()
        .map(entry -> NamedContributor.of(entry.getKey(), entry.getValue()))
        .iterator();
  }
}
