package com.openclassrooms.starterjwt.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.UserService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

  @Mock
  private UserService userService;

  @Mock
  private UserMapper userMapper;

  @InjectMocks
  private UserController userController;

  @Test
  void shouldFindUserById() {

    // GIVEN
    User user = new User();
    user.setId(1L);
    user.setEmail("test@test.com");

    UserDto dto = new UserDto();
    dto.setId(1L);
    dto.setEmail("test@test.com");

    when(userService.findById(1L)).thenReturn(user);
    when(userMapper.toDto(user)).thenReturn(dto);

    // WHEN
    ResponseEntity<?> response = userController.findById("1");

    // THEN
    assertEquals(200, response.getStatusCodeValue());
    assertNotNull(response.getBody());
    assertEquals(dto, response.getBody());

    verify(userService).findById(1L);
    verify(userMapper).toDto(user);
  }

  /**
   * TEST : GET /api/user/{id} - not found
   */
  @Test
  void shouldReturnNotFound() {

    when(userService.findById(1L)).thenReturn(null);

    ResponseEntity<?> response = userController.findById("1");

    assertEquals(404, response.getStatusCodeValue());
  }

  /**
   * TEST : DELETE /api/user/{id}
   * Cas OK : utilisateur connecté = propriétaire
   */
  @Test
  void shouldDeleteUser() {

    User user = new User();
    user.setId(1L);
    user.setEmail("test@test.com");

    when(userService.findById(1L)).thenReturn(user);

    // 🔐 Simulation du SecurityContext (user connecté)
    UserDetails userDetails = mock(UserDetails.class);
    when(userDetails.getUsername()).thenReturn("test@test.com");

    SecurityContextHolder.getContext().setAuthentication(
        new UsernamePasswordAuthenticationToken(userDetails, null));

    ResponseEntity<?> response = userController.save("1");

    assertEquals(200, response.getStatusCodeValue());

    verify(userService).delete(1L);
  }

  /**
   * TEST : DELETE /api/user/{id}
   * Cas unauthorized (mauvais utilisateur connecté)
   */
  @Test
  void shouldReturnUnauthorized() {

    User user = new User();
    user.setId(1L);
    user.setEmail("real@mail.com");

    when(userService.findById(1L)).thenReturn(user);

    UserDetails userDetails = mock(UserDetails.class);
    when(userDetails.getUsername()).thenReturn("fake@mail.com");

    SecurityContextHolder.getContext().setAuthentication(
        new UsernamePasswordAuthenticationToken(userDetails, null));

    ResponseEntity<?> response = userController.save("1");

    assertEquals(401, response.getStatusCodeValue());
  }

  @Test
  void shouldReturnBadRequestWhenUserIdIsInvalid() {

    ResponseEntity<?> response = userController.save("abc");

    assertEquals(400, response.getStatusCodeValue());

    verifyNoInteractions(userService);
  }

}