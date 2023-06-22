package com.modyo.example.adapters.web.dto;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GreetingDto implements Serializable {

  private String message;

}
