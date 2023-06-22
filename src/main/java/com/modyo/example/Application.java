package com.modyo.example;

import java.util.TimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"com.modyo.example", "be.modyo.commons"})
public class Application {

  public static void main(String[] args) {
    // Application uses UTC as default timezone to avoid diferences between local and aws runs
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    SpringApplication.run(Application.class, args);
  }

}
