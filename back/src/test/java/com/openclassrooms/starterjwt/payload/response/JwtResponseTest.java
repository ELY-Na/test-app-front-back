package com.openclassrooms.starterjwt.payload.response;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class JwtResponseTest {
  @Test
  void shoulCreatedJwtResponse() {

    JwtResponse response = new JwtResponse(
        "token",
        1L,
        "John Doe",
        "John",
        "Doe",
        true);

    assertEquals("token", response.getToken());
    assertEquals(1L, response.getId());
    assertEquals("John Doe", response.getUsername());
    assertEquals("John", response.getFirstName());
    assertEquals("Doe", response.getLastName());

    assertTrue(response.getAdmin());

    assertEquals("Bearer", response.getType());

  }
}
