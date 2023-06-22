package com.modyo.example.application.port.out;

import com.modyo.example.domain.model.User;
import java.util.List;
import lombok.Builder;

public interface LoadUsersPort {

  List<User> getAll();

  User getOne(UserId userId);

  User create(UserCommandRequest data);

  User update(UserId userId, UserCommandRequest data);

  void delete(UserId userId);

  @Builder
  record UserCommandRequest(String email, String firstName, String lastName) {

  }

  record UserId(Integer id) {

  }
}
