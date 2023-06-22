package com.modyo.example.adapters.restclient.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.modyo.example.adapters.restclient.users.dto.ListResourceResponse;
import com.modyo.example.adapters.restclient.users.dto.SingleResourceResponse;
import com.modyo.example.adapters.restclient.users.dto.UserData;
import com.modyo.example.application.port.out.LoadUsersPort.UserCommandRequest;
import com.modyo.example.application.port.out.LoadUsersPort.UserId;
import com.modyo.example.domain.model.User;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UsersRestAdapterTest {

  @Mock
  UsersRestClient usersRestClient;
  UserDataMapper userDataMapper;

  UsersRestAdapter usersRestAdapter;

  @BeforeEach
  void setUp() {
    userDataMapper = new UserDataMapperImpl();
    usersRestAdapter = new UsersRestAdapter(usersRestClient, userDataMapper);
  }

  @Test
  void getNone() {

    ListResourceResponse<UserData> response = new ListResourceResponse<>(List.of());
    given(usersRestClient.getUsers()).willReturn(response);

    List<User> all = usersRestAdapter.getAll();

    assertThat(all).isEmpty();
  }

  @Test
  void getAll() {

    UserData userData1 = new UserData(1, "@", "fn", "ln");
    UserData userData2 = new UserData(2, "@", "fn", "ln");
    List<UserData> usersData = List.of(userData1, userData2);
    ListResourceResponse<UserData> response = new ListResourceResponse<>(usersData);
    given(usersRestClient.getUsers()).willReturn(response);

    List<User> all = usersRestAdapter.getAll();

    assertThat(all).hasSize(usersData.size());
  }

  @Test
  void getOne() {

    UserData userData = new UserData(1, "@", "fn", "ln");

    given(usersRestClient.getUser(1))
        .willReturn(new SingleResourceResponse<>(userData));

    User user = usersRestAdapter.getOne(new UserId(1));

    assertThat(user).isNotNull();
    assertThat(user.getId()).isEqualTo(1);
    assertThat(user.getName()).isEqualTo("fn ln");
    assertThat(user.getEmail()).isEqualTo("@");
  }

  @Test
  void create() {

    UserCommandRequest userCreateRequest = new UserCommandRequest("@", "fn", "ln");

    given(usersRestClient.create(UserData.of(userCreateRequest)))
        .willReturn(new UserData(2, "@", "fn", "ln"));

    User user = usersRestAdapter.create(userCreateRequest);

    assertThat(user).isNotNull();
    assertThat(user.getId()).isEqualTo(2);
    assertThat(user.getName()).isEqualTo("fn ln");
    assertThat(user.getEmail()).isEqualTo("@");
  }

  @Test
  void update() {

    UserCommandRequest userUpdateRequest = new UserCommandRequest("@", "fn", "ln");

    given(usersRestClient.update(2, UserData.of(userUpdateRequest)))
        .willReturn(new UserData(2, "@", "fn", "ln"));

    User user = usersRestAdapter.update(new UserId(2), userUpdateRequest);

    assertThat(user).isNotNull();
    assertThat(user.getId()).isEqualTo(2);
    assertThat(user.getName()).isEqualTo("fn ln");
    assertThat(user.getEmail()).isEqualTo("@");
  }

  @Test
  void delete() {

    usersRestAdapter.delete(new UserId(1));

    then(usersRestClient).should().delete(1);
  }
}
