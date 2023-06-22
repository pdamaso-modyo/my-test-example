package com.modyo.example.adapters.web.users.dto;

import com.modyo.example.application.port.in.UsersUseCase.UserCommand;
import lombok.Data;

@Data
public class UserRequest {

  private String nombre;
  private String apellido;
  private String correo;

  public UserCommand toCommand() {
    return new UserCommand(
        this.getCorreo(),
        this.getNombre(),
        this.getApellido());
  }
}
