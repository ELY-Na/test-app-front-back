package com.openclassrooms.starterjwt.security.jwt;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.AuthenticationException;

class AuthEntryPointJwtTest {

  @Test
  void shouldReturnUnauthorizedResponse() throws Exception {

    // GIVEN : on simule une erreur d'authentification
    AuthEntryPointJwt entryPoint = new AuthEntryPointJwt();

    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setServletPath("/api/test");

    MockHttpServletResponse response = new MockHttpServletResponse();

    AuthenticationException exception = new AuthenticationException("Unauthorized") {
    };

    // WHEN : appel du handler 401
    entryPoint.commence(request, response, exception);

    // THEN : vérifications HTTP
    assertEquals(401, response.getStatus());
    assertEquals("application/json", response.getContentType());

    // vérifie que le body contient bien des infos JSON
    String content = response.getContentAsString();

    assertTrue(content.contains("Unauthorized"));
    assertTrue(content.contains("status"));
    assertTrue(content.contains("/api/test"));
  }
}