package com.openclassrooms.starterjwt.models;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class UserTest {

  @Test
  void shouldUseBuilder() {

    User user = User.builder()
        .id(1L)
        .email("admin@test.com")
        .firstName("John")
        .lastName("Doe")
        .password("password")
        .admin(true)
        .build();

    assertEquals(1L, user.getId());
    assertEquals("admin@test.com", user.getEmail());
    assertTrue(user.isAdmin());
  }

  @Test
  void shouldEqualWhenSameId() {

    User u1 = new User();
    u1.setId(1L);

    User u2 = new User();
    u2.setId(1L);

    assertEquals(u1, u2);
  }

  @Test
  void shouldNotEqualWhenDifferentId() {

    User u1 = new User();
    u1.setId(1L);

    User u2 = new User();
    u2.setId(2L);

    assertNotEquals(u1, u2);
  }
}