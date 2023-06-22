package com.modyo.example.adapters.restclient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RestAdapterTest {

  RestAdapter restAdapter;

  @BeforeEach
  void setUp() {
    restAdapter = new RestAdapter();
  }

  @Test
  void testLoadGreeting() {
    assertEquals("Good Afternoon", restAdapter.loadGreeting(13));
    assertEquals("Good Night", restAdapter.loadGreeting(21));
    assertEquals("Good Night", restAdapter.loadGreeting(1));
    assertEquals("Good Morning", restAdapter.loadGreeting(8));
  }

  @Test
  void test() {
    assertThat(restAdapter).isNotNull();
  }
}
