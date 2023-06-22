package com.modyo.example.adapters.restclient.users;

import com.modyo.example.adapters.restclient.users.dto.ListResourceResponse;
import com.modyo.example.adapters.restclient.users.dto.SingleResourceResponse;
import com.modyo.example.adapters.restclient.users.dto.UserData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    value = "users-client"
)
public interface UsersRestClient {

  @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
  ListResourceResponse<UserData> getUsers();

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  SingleResourceResponse<UserData> getUser(@PathVariable Integer id);

  @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
  UserData create(@RequestBody UserData request);

  @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  UserData update(@PathVariable Integer id, @RequestBody UserData request);

  @DeleteMapping(value = "/{id}")
  void delete(@PathVariable Integer id);

  @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
  void health();
}
