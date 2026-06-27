package com.openclassrooms.starterjwt.payload.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageResponseTest {

  @Test
  void shouldCreateMessageResponse() {

    MessageResponse response = new MessageResponse("Message initial");

    assertEquals("Message initial", response.getMessage());
  }

  @Test
  void shouldUpdateMessage() {

    MessageResponse response = new MessageResponse("Message initial");

    response.setMessage("Nouveau message");

    assertEquals("Nouveau message", response.getMessage());
  }
}