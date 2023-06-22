package com.modyo.example.application.port.in;

import com.modyo.example.domain.model.User;
import jakarta.validation.Valid;
import java.util.List;

public interface UsersUseCase {

  List<User> getAll();

  User get(Integer id);

  User create(@Valid UserCommand command);

  User update(Integer id, @Valid UserCommand command);

  void delete(Integer id);

  record UserCommand(String email, String firstName, String lastName) {

  }
}
