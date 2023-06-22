package com.modyo.example.adapters.web.users.dto;

import com.modyo.example.domain.model.User;
import java.util.List;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface UserResponseMapper {

  @Mapping(target = "nombre", source = "name")
  @Mapping(target = "correo", source = "email")
  UserResponse map(User user);

  List<UserResponse> map(List<User> users);
}
