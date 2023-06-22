package com.modyo.example.adapters.restclient.users;

import com.modyo.example.adapters.restclient.users.dto.UserData;
import com.modyo.example.domain.model.User;
import java.util.List;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface UserDataMapper {

  @Mapping(target = "name", source = "user", qualifiedByName = "mapName")
  User map(UserData user);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "name", source = "user", qualifiedByName = "mapName")
  User map(Integer id, UserData user);

  List<User> map(List<UserData> users);

  @Named("mapName")
  default String mapName(UserData user) {
    return String.join(" ", user.getFirstName(), user.getLastName());
  }
}
