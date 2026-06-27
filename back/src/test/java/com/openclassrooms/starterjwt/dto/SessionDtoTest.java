package com.openclassrooms.starterjwt.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SessionDtoTest {

  @Test
  void shouldCreateSessionDtoWithAllArgsConstructor() {

    Date date = new Date();

    LocalDateTime createdAt = LocalDateTime.of(2026, 6, 23, 10, 30);
    LocalDateTime updatedAt = LocalDateTime.of(2026, 6, 23, 11, 0);

    List<Long> users = Arrays.asList(1L, 2L);

    SessionDto dto = new SessionDto(
        1L,
        "Yoga débutant",
        date,
        10L,
        "Cours de yoga",
        users,
        createdAt,
        updatedAt);

    assertEquals(1L, dto.getId());
    assertEquals("Yoga débutant", dto.getName());
    assertEquals(date, dto.getDate());
    assertEquals(10L, dto.getTeacher_id());
    assertEquals("Cours de yoga", dto.getDescription());

    assertEquals(users, dto.getUsers());

    assertEquals(createdAt, dto.getCreatedAt());
    assertEquals(updatedAt, dto.getUpdatedAt());
  }

  @Test
  void shouldCreateSessionDtoWithNoArgsConstructor() {

    SessionDto dto = new SessionDto();

    dto.setId(1L);
    dto.setName("Yoga Débutant");

    assertEquals(1L, dto.getId());
    assertEquals("Yoga Débutant", dto.getName());
  }

  @Test
  void shouldTestEquals() {
    SessionDto dto1 = new SessionDto();
    dto1.setId(1L);
    dto1.setName("Yoga");

    SessionDto dto2 = new SessionDto();
    dto2.setId(1L);
    dto2.setName("Yoga");

    assertEquals(dto1, dto2);
  }

  @Test
  void shouldGenerateToString() {
    SessionDto dto = new SessionDto();
    dto.setId(1L);

    String result = dto.toString();

    assertNotNull(result);
    assertFalse(result.isEmpty());
  }
}