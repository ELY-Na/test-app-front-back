package com.openclassrooms.starterjwt.security.jwt;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import javax.servlet.FilterChain;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;

import com.openclassrooms.starterjwt.security.services.UserDetailsServiceImpl;

public class AuthTokenFilterTest {
  @Test
  void shouldDoNothingWhenNoJwt() throws Exception {

    AuthTokenFilter filter = new AuthTokenFilter();

    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();

    FilterChain chain = mock(FilterChain.class);

    // WHEN
    filter.doFilterInternal(request, response, chain);

    // THEN
    verify(chain).doFilter(request, response);
  }

  @Test
  void shouldAuthenticateWhenJwtIsValid() throws Exception {

    AuthTokenFilter filter = new AuthTokenFilter();

    // mocks
    JwtUtils jwtUtils = mock(JwtUtils.class);

    UserDetailsServiceImpl userDetailsService = mock(UserDetailsServiceImpl.class);

    // injection manuelle (important car pas Spring)
    setField(filter, "jwtUtils", jwtUtils);
    setField(filter, "userDetailsService", userDetailsService);

    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader("Authorization", "Bearer fakeToken");

    MockHttpServletResponse response = new MockHttpServletResponse();
    FilterChain chain = mock(FilterChain.class);

    UserDetails userDetails = mock(UserDetails.class);

    when(jwtUtils.validateJwtToken("fakeToken")).thenReturn(true);
    when(jwtUtils.getUserNameFromJwtToken("fakeToken")).thenReturn("test@test.com");
    when(userDetailsService.loadUserByUsername("test@test.com"))
        .thenReturn(userDetails);

    when(userDetails.getAuthorities()).thenReturn(new ArrayList<>());

    // WHEN
    filter.doFilterInternal(request, response, chain);

    // THEN
    verify(jwtUtils).validateJwtToken("fakeToken");
    verify(userDetailsService).loadUserByUsername("test@test.com");
    verify(chain).doFilter(request, response);
  }

  @Test
  void shouldSkipAuthenticationWhenJwtInvalid() throws Exception {

    AuthTokenFilter filter = new AuthTokenFilter();

    JwtUtils jwtUtils = mock(JwtUtils.class);
    UserDetailsServiceImpl userDetailsService = mock(UserDetailsServiceImpl.class);

    setField(filter, "jwtUtils", jwtUtils);
    setField(filter, "userDetailsService", userDetailsService);

    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader("Authorization", "Bearer badToken");

    MockHttpServletResponse response = new MockHttpServletResponse();
    FilterChain chain = mock(FilterChain.class);

    when(jwtUtils.validateJwtToken("badToken")).thenReturn(false);

    // WHEN
    filter.doFilterInternal(request, response, chain);

    // THEN
    verify(chain).doFilter(request, response);
    verify(userDetailsService, never()).loadUserByUsername(any());
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

}
