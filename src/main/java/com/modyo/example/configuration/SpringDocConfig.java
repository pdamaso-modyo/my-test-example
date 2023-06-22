package com.modyo.example.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SpringDocConfig {

  private final BuildProperties buildProperties;

  @Bean
  public OpenAPI apiInfo() {
    Info info = new Info()
        .title("my-test-example")
        .description("My Test Example Microservice.")
        .version(buildProperties.getVersion());
    return new OpenAPI().info(info);
  }

  @Bean
  public GroupedOpenApi api() {
    return GroupedOpenApi.builder()
        .group("api")
        .pathsToMatch("/**")
        .packagesToScan("com.modyo.example")
        .build();
  }

  @Bean
  public GroupedOpenApi health() {
    return GroupedOpenApi.builder()
        .group("health")
        .pathsToMatch("/actuator/**")
        .packagesToScan("org.springframework.boot")
        .build();
  }
}
