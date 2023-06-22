package be.modyo.commons.health;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.NamedContributor;

class IntegrationsHealthContributorTest {

  IntegrationsHealthContributor integrationsHealthContributor;
  private IntegrationHealthIndicator healthIndicator;

  @BeforeEach
  void setUp() {
    HealthIndicator health = () -> Health.up().build();
    healthIndicator = IntegrationHealthIndicator.of("mock", health);
    integrationsHealthContributor = new IntegrationsHealthContributor(List.of(healthIndicator));
  }

  @Test
  void getContributor() {

    HealthContributor healthContributor = integrationsHealthContributor.getContributor("mock");

    assertThat(healthContributor).isEqualTo(healthIndicator.getHealthIndicator());
  }

  @Test
  void iterator() {

    Iterator<NamedContributor<HealthContributor>> iterator = integrationsHealthContributor.iterator();

    assertThat(iterator).hasNext();
    iterator.next();
    assertThat(iterator).isExhausted();
  }
}
