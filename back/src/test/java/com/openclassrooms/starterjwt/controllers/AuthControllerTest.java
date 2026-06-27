package com.openclassrooms.starterjwt.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private JwtUtils jwtUtils;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private AuthController authController;

  /**
   * TEST LOGIN
   */
  @Test
  void shouldLoginUser() {

    LoginRequest request = new LoginRequest();
    request.setEmail("test@test.com");
    request.setPassword("1234");

    Authentication authentication = mock(Authentication.class);
    UserDetailsImpl userDetails = mock(UserDetailsImpl.class);

    when(userDetails.getUsername()).thenReturn("test@test.com");
    when(userDetails.getId()).thenReturn(1L);
    when(userDetails.getFirstName()).thenReturn("John");
    when(userDetails.getLastName()).thenReturn("Doe");

    when(authentication.getPrincipal()).thenReturn(userDetails);

    when(authenticationManager.authenticate(any()))
        .thenReturn(authentication);

    when(jwtUtils.generateJwtToken(authentication))
        .thenReturn("fake-jwt");

    ResponseEntity<?> response = authController.authenticateUser(request);

    assertEquals(200, response.getStatusCodeValue());

    JwtResponse body = (JwtResponse) response.getBody();
    assertEquals("test@test.com", body.getUsername());
    assertEquals("fake-jwt", body.getToken());
    assertFalse(body.getAdmin());
  }

  /**
   * TEST REGISTER SUCCESS
   */
  @Test
  void shouldRegisterUser() {

    SignupRequest request = new SignupRequest();
    request.setEmail("new@test.com");
    request.setPassword("1234");
    request.setFirstName("John");
    request.setLastName("Doe");

    when(userRepository.existsByEmail("new@test.com"))
        .thenReturn(false);

    when(passwordEncoder.encode("1234"))
        .thenReturn("encoded");

    ResponseEntity<?> response = authController.registerUser(request);

    assertEquals(200, response.getStatusCodeValue());

    verify(userRepository).save(any());
  }

  /**
   * TEST REGISTER FAIL (email exist)
   */
  @Test
  void shouldReturnBadRequestWhenEmailExists() {

    SignupRequest request = new SignupRequest();
    request.setEmail("exists@test.com");

    when(userRepository.existsByEmail("exists@test.com"))
        .thenReturn(true);

    ResponseEntity<?> response = authController.registerUser(request);

    assertEquals(400, response.getStatusCodeValue());
  }

  @Test
  void shouldLoginAdminUser() {

    LoginRequest request = new LoginRequest();
    request.setEmail("admin@test.com");
    request.setPassword("1234");

    Authentication authentication = mock(Authentication.class);

    UserDetailsImpl userDetails = mock(UserDetailsImpl.class);

    User user = new User();
    user.setAdmin(true);

    when(authenticationManager.authenticate(any()))
        .thenReturn(authentication);

    when(authentication.getPrincipal())
        .thenReturn(userDetails);

    when(userDetails.getUsername())
        .thenReturn("admin@test.com");

    when(userDetails.getId())
        .thenReturn(1L);

    when(userDetails.getFirstName())
        .thenReturn("John");

    when(userDetails.getLastName())
        .thenReturn("Doe");

    when(jwtUtils.generateJwtToken(authentication))
        .thenReturn("jwt-token");

    when(userRepository.findByEmail("admin@test.com"))
        .thenReturn(Optional.of(user));

    ResponseEntity<?> response = authController.authenticateUser(request);
    assertTrue(((JwtResponse) response.getBody()).getAdmin());

    assertEquals(200, response.getStatusCodeValue());
  }
}