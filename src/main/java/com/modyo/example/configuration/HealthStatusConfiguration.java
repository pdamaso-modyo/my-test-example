package com.modyo.example.configuration;

import be.modyo.commons.health.DefaultStatusHealthIndicator;
import be.modyo.commons.health.IntegrationHealthIndicator;
import be.modyo.commons.health.IntegrationsHealthContributor;
import com.modyo.example.adapters.restclient.users.UsersRestClient;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class HealthStatusConfiguration {

  @Bean
  public IntegrationsHealthContributor externalHealthContributor(
      List<IntegrationHealthIndicator> healthIndicators) {
    return new IntegrationsHealthContributor(healthIndicators);
  }

  @Bean
  public IntegrationHealthIndicator usersServiceHealthIndicator(
      @Lazy UsersRestClient usersRestClient) {
    return IntegrationHealthIndicator.of("users",
        new DefaultStatusHealthIndicator(usersRestClient::health)
    );
  }
}
