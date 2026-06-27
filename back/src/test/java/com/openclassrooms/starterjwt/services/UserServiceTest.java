package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  @Test
  void shouldFindById() {

    // GIVEN (préparation des données)
    User user = new User();
    user.setId(1L);

    when(userRepository.findById(1L))
        .thenReturn(Optional.of(user));

    // WHEN (appel du service)
    User result = userService.findById(1L);

    // THEN (vérifications)
    assertNotNull(result);
    assertEquals(1L, result.getId());

    verify(userRepository, times(1)).findById(1L);
  }

  @Test
  void shouldReturnNullWhenUserNotFound() {

    when(userRepository.findById(99L))
        .thenReturn(Optional.empty());

    User result = userService.findById(99L);

    assertNull(result);

    verify(userRepository, times(1)).findById(99L);
  }

  @Test
  void shouldDelete() {

    Long userId = 1L;

    doNothing().when(userRepository).deleteById(userId);

    userService.delete(userId);

    verify(userRepository, times(1)).deleteById(userId);
  }
}