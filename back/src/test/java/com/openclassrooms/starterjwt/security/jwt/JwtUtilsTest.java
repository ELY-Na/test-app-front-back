package com.openclassrooms.starterjwt.security.jwt;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;

import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;

class JwtUtilsTest {

  private JwtUtils jwtUtils;

  /**
   * Avant chaque test :
   * on crée JwtUtils manuellement car Spring n'injecte pas les @Value ici.
   */
  @BeforeEach
  void setUp() {
    jwtUtils = new JwtUtils();

    // Injection manuelle des valeurs du application.properties
    setField(jwtUtils, "jwtSecret", "openclassrooms");
    setField(jwtUtils, "jwtExpirationMs", 86400000);
  }

  /**
   * TEST 1 : génération d’un token JWT
   * Objectif : vérifier que generateJwtToken retourne une string non vide
   */
  @Test
  void shouldGenerateJwtToken() {

    Authentication authentication = mock(Authentication.class);

    UserDetailsImpl user = UserDetailsImpl.builder()
        .id(1L)
        .firstName("John")
        .lastName("Doe")
        .username("test@test.com")
        .admin(true)
        .build();

    when(authentication.getPrincipal()).thenReturn(user);

    String token = jwtUtils.generateJwtToken(authentication);

    assertNotNull(token);
    assertFalse(token.isEmpty());
  }

  /**
   * TEST 2 : récupération du username depuis un token
   */
  @Test
  void shouldGetUsernameFromToken() {

    Authentication authentication = mock(Authentication.class);

    UserDetailsImpl user = UserDetailsImpl.builder()
        .username("test@test.com")
        .build();

    when(authentication.getPrincipal()).thenReturn(user);

    String token = jwtUtils.generateJwtToken(authentication);

    String username = jwtUtils.getUserNameFromJwtToken(token);

    assertEquals("test@test.com", username);
  }

  /**
   * TEST 3 : validation d’un token valide
   */
  @Test
  void shouldValidateValidToken() {

    Authentication authentication = mock(Authentication.class);

    UserDetailsImpl user = UserDetailsImpl.builder()
        .username("test@test.com")
        .build();

    when(authentication.getPrincipal()).thenReturn(user);

    String token = jwtUtils.generateJwtToken(authentication);

    assertTrue(jwtUtils.validateJwtToken(token));
  }

  /**
   * Utilitaire de test :
   * permet d’injecter les champs @Value (car Spring n’est pas lancé)
   */
  private void setField(Object target, String fieldName, Object value) {
    try {
      java.lang.reflect.Field field = target.getClass().getDeclaredField(fieldName);
      field.setAccessible(true);
      field.set(target, value);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void shouldInvalidateMalformedToken() {
    String fakeToken = "not.a.valid.token";

    assertFalse(jwtUtils.validateJwtToken(fakeToken));
  }

  @Test
  void shouldInvalidateNullToken() {
    assertFalse(jwtUtils.validateJwtToken(null));
  }

  @Test
  void shouldReturnFalseForEmptyToken() {
    boolean result = jwtUtils.validateJwtToken("");

    assertFalse(result);
  }

  @Test
  void shouldReturnFalseForInvalidSignature() {

    JwtUtils otherJwtUtils = new JwtUtils();

    setField(otherJwtUtils, "jwtSecret", "anotherSecret");
    setField(otherJwtUtils, "jwtExpirationMs", 86400000);

    Authentication authentication = mock(Authentication.class);

    UserDetailsImpl user = UserDetailsImpl.builder()
        .username("test@test.com")
        .build();

    when(authentication.getPrincipal()).thenReturn(user);

    String token = otherJwtUtils.generateJwtToken(authentication);

    assertFalse(jwtUtils.validateJwtToken(token));
  }

  @Test
  void shouldReturnFalseForExpiredToken() {

    JwtUtils expiredJwtUtils = new JwtUtils();

    setField(expiredJwtUtils, "jwtSecret", "openclassrooms");
    setField(expiredJwtUtils, "jwtExpirationMs", -1000);

    Authentication authentication = mock(Authentication.class);

    UserDetailsImpl user = UserDetailsImpl.builder()
        .username("test@test.com")
        .build();

    when(authentication.getPrincipal()).thenReturn(user);

    String token = expiredJwtUtils.generateJwtToken(authentication);

    assertFalse(jwtUtils.validateJwtToken(token));
  }

  @Test
  void shouldUseAllSetters() {

    JwtResponse response = new JwtResponse(
        "token",
        1L,
        "Bearer",
        "John",
        "Doe",
        false);

    response.setToken("newToken");
    response.setType("newType");
    response.setId(2L);
    response.setUsername("newUsername");
    response.setFirstName("Jane");
    response.setLastName("Doe");
    response.setAdmin(true);

    assertEquals("newToken", response.getToken());
    assertEquals("newType", response.getType());
    assertEquals(2L, response.getId());
    assertEquals("newUsername", response.getUsername());
    assertEquals("Jane", response.getFirstName());
    assertEquals("Doe", response.getLastName());
    assertTrue(response.getAdmin());
  }

  @Test
  void shouldHandleUnsupportedJwtException() {

    String invalidJwt = "eyJhbGciOiJub25lIn0.invalid.signature";

    boolean result = jwtUtils.validateJwtToken(invalidJwt);

    assertFalse(result);
  }
}