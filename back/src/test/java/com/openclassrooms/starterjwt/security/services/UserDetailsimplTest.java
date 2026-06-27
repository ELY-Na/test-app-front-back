package com.openclassrooms.starterjwt.security.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

class UserDetailsImplTest {

  @Test
  void shouldBuildUserDetails() {

    UserDetailsImpl user = UserDetailsImpl.builder()
        .id(1L)
        .username("test@test.com")
        .firstName("John")
        .lastName("Doe")
        .password("secret")
        .admin(true)
        .build();

    assertEquals(1L, user.getId());
    assertEquals("test@test.com", user.getUsername());
    assertEquals("John", user.getFirstName());
    assertEquals("Doe", user.getLastName());
    assertEquals("secret", user.getPassword());
    assertTrue(user.getAdmin());
  }

  @Test
  void shouldReturnEmptyAuthorities() {

    UserDetailsImpl user = UserDetailsImpl.builder()
        .id(1L)
        .build();

    Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

    assertNotNull(authorities);
    assertTrue(authorities.isEmpty());
  }

  @Test
  void shouldReturnTrueForSecurityFlags() {

    UserDetailsImpl user = UserDetailsImpl.builder()
        .id(1L)
        .build();

    assertTrue(user.isAccountNonExpired());
    assertTrue(user.isAccountNonLocked());
    assertTrue(user.isCredentialsNonExpired());
    assertTrue(user.isEnabled());
  }

  @Test
  void shouldCompareUsersWithEquals() {

    UserDetailsImpl user1 = UserDetailsImpl.builder()
        .id(1L)
        .build();

    UserDetailsImpl user2 = UserDetailsImpl.builder()
        .id(1L)
        .build();

    UserDetailsImpl user3 = UserDetailsImpl.builder()
        .id(2L)
        .build();

    assertEquals(user1, user2);
    assertNotEquals(user1, user3);
    assertEquals(user1, user1);
    assertNotEquals(user1, null);
  }

  // @Test
  // void shouldReturnFalseWhenObjectIsNull() {
  // UserDetailsImpl user = UserDetailsImpl.builder()
  // .id(1L)
  // .build();

  // assertFalse(user.equals(null));
  // }

  @Test
  void shouldReturnFalseWhenDifferentClass() {
    UserDetailsImpl user = UserDetailsImpl.builder()
        .id(1L)
        .build();

    assertFalse(user.equals("not a UserDetailsImpl"));
  }

  @Test
  void shouldBuildUserDetailsImplWithBuilder() {

    UserDetailsImpl user = UserDetailsImpl.builder()
        .admin(true)
        .build();

    assertNotNull(user);
    assertTrue(user.getAdmin());
  }

  @Test
  void shouldBuildUserDetailsImplDefault() {

    UserDetailsImpl user = UserDetailsImpl.builder()
        .build();

    assertNotNull(user);
  }

  @Test
  void shouldCoverBuilderToString() {

    UserDetailsImpl.UserDetailsImplBuilder builder = UserDetailsImpl.builder()
        .id(1L)
        .username("test@test.com")
        .firstName("John")
        .lastName("Doe")
        .password("secret")
        .admin(true);

    // 👇 force l'exécution de toString() du builder
    String result = builder.toString();

    assertNotNull(result);
    assertTrue(result.contains("UserDetailsImplBuilder"));
  }
}