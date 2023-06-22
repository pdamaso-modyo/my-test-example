package com.modyo.example.adapters.restclient;

import com.modyo.example.application.port.out.OutputPort;
import org.springframework.stereotype.Service;

@Service
public class RestAdapter implements OutputPort {

  @Override
  public String loadGreeting(int hour) {
    String greeting;
    if (hour >= 5 && hour < 12) {
      greeting = "Good Morning";
    } else if (hour >= 12 && hour < 20) {
      greeting = "Good Afternoon";
    } else {
      greeting = "Good Night";
    }
    return greeting;
  }
}
