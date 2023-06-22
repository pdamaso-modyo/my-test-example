package com.modyo.example.adapters.restclient.users;

import com.modyo.example.adapters.restclient.users.dto.ListResourceResponse;
import com.modyo.example.adapters.restclient.users.dto.SingleResourceResponse;
import com.modyo.example.adapters.restclient.users.dto.UserData;
import com.modyo.example.application.port.out.LoadUsersPort;
import com.modyo.example.domain.model.User;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersRestAdapter implements LoadUsersPort {

  private final UsersRestClient usersRestClient;
  private final UserDataMapper userDataMapper;

  @Override
  public List<User> getAll() {
    ListResourceResponse<UserData> users = usersRestClient.getUsers();
    return Optional.ofNullable(users)
        .map(ListResourceResponse::getData)
        .map(userDataMapper::map)
        .orElse(List.of());
  }

  @Override
  public User getOne(UserId userId) {
    var user = usersRestClient.getUser(userId.id());
    return Optional.ofNullable(user)
        .map(SingleResourceResponse::getData)
        .map(userDataMapper::map)
        .orElse(User.empty());
  }

  @Override
  public User create(UserCommandRequest userCreateRequest) {
    UserData request = UserData.of(userCreateRequest);
    log.debug("action=create, request={}", request);
    UserData userData = usersRestClient.create(request);
    return userDataMapper.map(userData);
  }

  @Override
  public User update(UserId userId, UserCommandRequest userUpdateRequest) {
    Integer resourceId = userId.id();
    UserData request = UserData.of(userUpdateRequest);
    log.debug("action=update, id={}, request={}", resourceId, request);
    UserData userData = usersRestClient.update(resourceId, request);
    return userDataMapper.map(resourceId, userData);
  }

  @Override
  public void delete(UserId userId) {
    Integer resourceId = userId.id();
    log.debug("action=delete, id={}", resourceId);
    usersRestClient.delete(resourceId);
  }
}
