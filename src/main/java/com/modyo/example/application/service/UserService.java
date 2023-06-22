package com.modyo.example.application.service;

import com.modyo.example.application.port.in.UsersUseCase;
import com.modyo.example.application.port.out.LoadUsersPort;
import com.modyo.example.application.port.out.LoadUsersPort.UserCommandRequest;
import com.modyo.example.application.port.out.LoadUsersPort.UserId;
import com.modyo.example.domain.model.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UsersUseCase {


  private final LoadUsersPort loadUsersPort;

  @Override
  public List<User> getAll() {
    return loadUsersPort.getAll();
  }

  @Override
  public User get(Integer id) {
    return loadUsersPort.getOne(
        new UserId(id)
    );
  }

  @Override
  public User create(UserCommand userCommand) {
    return loadUsersPort.create(
        new UserCommandRequest(userCommand.email(), userCommand.firstName(), userCommand.lastName())
    );
  }

  @Override
  public User update(Integer id, UserCommand userCommand) {
    return loadUsersPort.update(
        new UserId(id),
        new UserCommandRequest(userCommand.email(), userCommand.firstName(), userCommand.lastName())
    );
  }

  @Override
  public void delete(Integer id) {
    loadUsersPort.delete(
        new UserId(id)
    );
  }
}
