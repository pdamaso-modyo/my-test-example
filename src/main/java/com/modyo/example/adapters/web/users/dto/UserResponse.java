package com.modyo.example.adapters.web.users.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class UserResponse {

  private Integer id;
  private String nombre;
  private String correo;
}
