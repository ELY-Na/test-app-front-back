package com.openclassrooms.starterjwt.security.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserDetailsServiceImpl service;

  @Test
  void shouldLoadUserByUsername() {

    User user = new User();

    user.setId(1L);
    user.setEmail("test@test.com");
    user.setFirstName("John");
    user.setLastName("Doe");
    user.setPassword("secret");

    when(userRepository.findByEmail("test@test.com"))
        .thenReturn(Optional.of(user));

    UserDetailsImpl result = (UserDetailsImpl) service.loadUserByUsername("test@test.com");

    assertEquals(1L, result.getId());
    assertEquals("test@test.com", result.getUsername());
    assertEquals("John", result.getFirstName());
    assertEquals("Doe", result.getLastName());
    assertEquals("secret", result.getPassword());
  }

  @Test
  void shouldThrowExceptionWhenUserNotFound() {

    when(userRepository.findByEmail("unknown@test.com"))
        .thenReturn(Optional.empty());

    assertThrows(
        UsernameNotFoundException.class,
        () -> service.loadUserByUsername("unknown@test.com"));
  }
}