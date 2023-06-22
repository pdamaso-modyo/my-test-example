package com.modyo.example.application.service;

import static org.mockito.BDDMockito.then;

import com.modyo.example.application.port.in.UsersUseCase.UserCommand;
import com.modyo.example.application.port.out.LoadUsersPort;
import com.modyo.example.application.port.out.LoadUsersPort.UserCommandRequest;
import com.modyo.example.application.port.out.LoadUsersPort.UserId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  LoadUsersPort loadUsersPort;

  @InjectMocks
  UserService userService;

  @Test
  void getAll() {

    userService.getAll();

    then(loadUsersPort).should().getAll();
  }

  @Test
  void get() {

    userService.get(1);

    then(loadUsersPort).should().getOne(new UserId(1));
  }

  @Test
  void create() {

    UserCommand userCommand = new UserCommand("@", "fn", "ln");
    userService.create(userCommand);

    then(loadUsersPort).should().create(new UserCommandRequest("@", "fn", "ln"));
  }

  @Test
  void update() {

    UserCommand userCommand = new UserCommand("@", "fn", "ln");
    userService.update(1, userCommand);

    then(loadUsersPort).should().update(new UserId(1), new UserCommandRequest("@", "fn", "ln"));
  }

  @Test
  void delete() {

    userService.delete(1);

    then(loadUsersPort).should().delete(new UserId(1));
  }
}
