package com.modyo.example.adapters.web.users;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.modyo.example.adapters.web.users.dto.UserResponseMapper;
import com.modyo.example.adapters.web.users.dto.UserResponseMapperImpl;
import com.modyo.example.application.port.in.UsersUseCase;
import com.modyo.example.application.port.in.UsersUseCase.UserCommand;
import com.modyo.example.domain.model.User;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest
@ContextConfiguration(classes = {ManageUsersController.class, UserResponseMapperImpl.class})
class ManageUsersControllerTest {

  @MockBean
  UsersUseCase usersUseCase;
  @Autowired
  UserResponseMapper userResponseMapper;

  @Autowired
  MockMvc mockMvc;

  @Test
  void getNone() throws Exception {

    given(usersUseCase.getAll())
        .willReturn(List.of());

    mockMvc.perform(get("/users"))
        .andDo(print())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json("[]"));
  }

  @Test
  void getEmpty() throws Exception {

    given(usersUseCase.getAll())
        .willReturn(List.of(User.empty()));

    mockMvc.perform(get("/users"))
        .andDo(print())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json("[{}]"));
  }

  @Test
  void getUsers() throws Exception {

    User user = new User(1, "name", "email@modyo.com");
    given(usersUseCase.getAll())
        .willReturn(List.of(user));

    String expectedResponse = """
        [{"id":1, "nombre":"name", "correo":"email@modyo.com"}]
        """;

    mockMvc.perform(get("/users"))
        .andDo(print())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(expectedResponse));
  }

  @Test
  void getOne() throws Exception {

    Integer userId = 1;
    User user = new User(userId, "name", "email@modyo.com");
    given(usersUseCase.get(userId))
        .willReturn(user);

    String expectedUser = """
        {"id":1, "nombre":"name", "correo":"email@modyo.com"}
        """;

    mockMvc.perform(get("/users/" + userId))
        .andDo(print())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(expectedUser));
  }

  @Test
  void create() throws Exception {

    String createUserRequest = """
        {
          "nombre": "first",
          "apellido": "last",
          "correo": "someone@modyo.com"
        }
        """;

    UserCommand userCommand = new UserCommand("someone@modyo.com", "first", "last");

    User user = new User(2, "first last", "someone@modyo.com");
    given(usersUseCase.create(userCommand))
        .willReturn(user);

    String expectedResponse = """
        {"id":2, "nombre":"first last", "correo":"someone@modyo.com"}
        """;

    mockMvc.perform(
            post("/users")
                .content(createUserRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andDo(print())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(expectedResponse));
  }

  @Test
  void update() throws Exception {

    Integer userId = 2;

    String updateUserRequest = """
        {
          "nombre": "other",
          "apellido": "another",
          "correo": "somebody@modyo.com"
        }
        """;

    UserCommand userCommand = new UserCommand("somebody@modyo.com", "other", "another");

    User user = new User(userId, "other another", "somebody@modyo.com");
    given(usersUseCase.update(userId, userCommand))
        .willReturn(user);

    String expectedResponse = """
        {"id":2, "nombre":"other another", "correo":"somebody@modyo.com"}
        """;


    mockMvc.perform(
            put("/users/" + userId)
                .content(updateUserRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andDo(print())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(expectedResponse));
  }

  @Test
  void delete() throws Exception {

    Integer userId = 2;

    mockMvc.perform(MockMvcRequestBuilders.delete("/users/" + userId))
        .andDo(print())
        .andExpect(status().isNoContent());

    then(usersUseCase).should().delete(userId);
  }
}
