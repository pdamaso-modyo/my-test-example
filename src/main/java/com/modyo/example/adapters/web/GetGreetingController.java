package com.modyo.example.adapters.web;

import com.modyo.example.adapters.web.dto.GreetingDto;
import com.modyo.example.application.port.in.InputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetGreetingController {

  private final InputPort port;

  @GetMapping(
      value = "/greeting",
      produces = "application/json")
  public ResponseEntity<GreetingDto> getGreetingUsingGET(
      @RequestParam(value = "name", defaultValue = "Modyo", required = false) String name
  ) {
    var response = GreetingDto.builder().message(port.getGreeting(name)).build();
    return ResponseEntity.ok(response);
  }
}
