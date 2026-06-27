package com.openclassrooms.starterjwt.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

class NotFoundExceptionTest {

  @Test
  void shouldHaveNotFoundResponseStatus() {

    ResponseStatus annotation = NotFoundException.class.getAnnotation(ResponseStatus.class);

    assertNotNull(annotation);
    assertEquals(HttpStatus.NOT_FOUND, annotation.value());
  }
}