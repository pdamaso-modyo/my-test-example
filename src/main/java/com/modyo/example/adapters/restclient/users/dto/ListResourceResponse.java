package com.modyo.example.adapters.restclient.users.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListResourceResponse<T> {

  private List<T> data;
}
