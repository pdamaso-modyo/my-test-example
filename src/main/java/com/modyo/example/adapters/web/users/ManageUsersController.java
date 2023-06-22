package com.modyo.example.adapters.web.users;

import com.modyo.example.adapters.web.users.dto.UserRequest;
import com.modyo.example.adapters.web.users.dto.UserResponse;
import com.modyo.example.adapters.web.users.dto.UserResponseMapper;
import com.modyo.example.application.port.in.UsersUseCase;
import com.modyo.example.application.port.in.UsersUseCase.UserCommand;
import com.modyo.example.domain.model.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class ManageUsersController {

  private final UsersUseCase usersUseCase;
  private final UserResponseMapper userResponseMapper;

  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  List<UserResponse> get() {
    List<User> users = usersUseCase.getAll();
    return userResponseMapper.map(users);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  UserResponse get(@PathVariable Integer id) {
    User user = usersUseCase.get(id);
    return userResponseMapper.map(user);
  }

  @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  UserResponse create(@RequestBody UserRequest userRequest) {
    UserCommand command = userRequest.toCommand();
    User user = usersUseCase.create(command);
    return userResponseMapper.map(user);
  }

  @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  UserResponse update(@PathVariable Integer id, @RequestBody UserRequest userRequest) {
    UserCommand command = userRequest.toCommand();
    User user = usersUseCase.update(id, command);
    return userResponseMapper.map(user);
  }

  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  @DeleteMapping(value = "/{id}")
  void delete(@PathVariable Integer id) {
    usersUseCase.delete(id);
  }
}
