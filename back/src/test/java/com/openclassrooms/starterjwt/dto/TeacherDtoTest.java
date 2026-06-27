package com.openclassrooms.starterjwt.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TeacherDtoTest {

  @Test
  void shouldCreateTeacherDtoWithAllArgsConstructor() {

    LocalDateTime createdAt = LocalDateTime.of(2026, 6, 23, 10, 30);
    LocalDateTime updatedAt = LocalDateTime.of(2026, 6, 23, 11, 0);

    TeacherDto dto = new TeacherDto(
        1L,
        "Doe",
        "John",
        createdAt,
        updatedAt);

    assertEquals(1L, dto.getId());
    assertEquals("Doe", dto.getLastName());
    assertEquals("John", dto.getFirstName());
    assertEquals(createdAt, dto.getCreatedAt());
    assertEquals(updatedAt, dto.getUpdatedAt());
  }

  @Test
  void shouldCreateTeacherDtoWithNoArgsConstructor() {

    TeacherDto dto = new TeacherDto();

    dto.setId(1L);
    dto.setLastName("Doe");
    dto.setFirstName("John");

    assertEquals(1L, dto.getId());
    assertEquals("Doe", dto.getLastName());
    assertEquals("John", dto.getFirstName());
  }

  @Test
  void shouldBeEqualWhenFieldsAreEqual() {
    LocalDateTime now = LocalDateTime.now();

    TeacherDto dto1 = new TeacherDto(1L, "Doe", "John", now, now);
    TeacherDto dto2 = new TeacherDto(1L, "Doe", "John", now, now);

    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());
  }

}