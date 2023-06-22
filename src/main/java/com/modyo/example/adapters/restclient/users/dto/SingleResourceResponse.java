package com.modyo.example.adapters.restclient.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleResourceResponse<T> {

  private T data;
}
