package com.openclassrooms.starterjwt.payload.request;

import static org.junit.jupiter.api.Assertions.*;

import javax.validation.*;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class SignupRequestTest {

  @Test
  void shouldSetAndGetFields() {
    SignupRequest request = new SignupRequest();

    request.setEmail("admin@test.com");
    request.setPassword("test1234");
    request.setFirstName("John");
    request.setLastName("Doe");

    assertEquals("admin@test.com", request.getEmail());
    assertEquals("test1234", request.getPassword());
  }

  @Test
  void shouldBeEqualWhenSameValues() {
    SignupRequest r1 = new SignupRequest();
    r1.setEmail("a@test.com");
    r1.setFirstName("John");
    r1.setLastName("Doe");
    r1.setPassword("123");

    SignupRequest r2 = new SignupRequest();
    r2.setEmail("a@test.com");
    r2.setFirstName("John");
    r2.setLastName("Doe");
    r2.setPassword("123");

    assertEquals(r1, r2);
  }

  @Test
  void shouldHaveConsistentHashCode() {
    SignupRequest r1 = new SignupRequest();
    r1.setEmail("a@test.com");

    SignupRequest r2 = new SignupRequest();
    r2.setEmail("a@test.com");

    assertEquals(r1.hashCode(), r2.hashCode());
  }

  @Test
  void shouldFailValidationWhenEmailIsBlank() {
    SignupRequest request = new SignupRequest();
    request.setEmail("");

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    Set<ConstraintViolation<SignupRequest>> violations = validator.validate(request);

    assertFalse(violations.isEmpty());
  }
}