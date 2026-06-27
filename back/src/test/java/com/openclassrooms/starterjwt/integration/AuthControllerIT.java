package com.openclassrooms.starterjwt.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerIT {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void shouldFailLogin_whenBadCredentials() throws Exception {

    String body = "{\"email\":\"wrong@test.com\",\"password\":\"wrong\"}";

    mockMvc.perform(post("/api/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(body))
        .andExpect(status().isUnauthorized());
  }
}