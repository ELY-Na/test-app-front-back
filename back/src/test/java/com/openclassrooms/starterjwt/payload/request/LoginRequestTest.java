package com.openclassrooms.starterjwt.payload.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestTest {

  @Test
  void shouldCreateLoginRequest() {

    LoginRequest request = new LoginRequest();

    request.setEmail("admin@test.com");
    request.setPassword("test1234");

    assertEquals("admin@test.com", request.getEmail());
    assertEquals("test1234", request.getPassword());
  }
}