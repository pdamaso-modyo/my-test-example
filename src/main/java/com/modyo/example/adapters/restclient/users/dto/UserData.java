package com.modyo.example.adapters.restclient.users.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modyo.example.application.port.out.LoadUsersPort.UserCommandRequest;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@JsonInclude(Include.NON_NULL)
public class UserData implements Serializable {

  private Integer id;
  private String email;
  @JsonProperty("first_name")
  private String firstName;
  @JsonProperty("last_name")
  private String lastName;

  public static UserData of(UserCommandRequest userCreateRequest) {
    return UserData.builder()
        .email(userCreateRequest.email())
        .firstName(userCreateRequest.firstName())
        .lastName(userCreateRequest.lastName())
        .build();
  }
}
