package com.openclassrooms.starterjwt.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

class BadRequestExceptionTest {

  @Test
  void shouldHaveBadRequestResponseStatus() {

    ResponseStatus annotation = BadRequestException.class.getAnnotation(ResponseStatus.class);

    assertNotNull(annotation);
    assertEquals(HttpStatus.BAD_REQUEST, annotation.value());
  }
}