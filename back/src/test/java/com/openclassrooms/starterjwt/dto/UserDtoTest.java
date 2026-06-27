//

package com.openclassrooms.starterjwt.dto;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoTest {

  @Test
  void shouldCreateUserDto() {

    LocalDateTime createdAt = LocalDateTime.of(2026, 6, 23, 10, 30);
    LocalDateTime updatedAt = LocalDateTime.of(2026, 6, 23, 11, 0);

    UserDto dto = new UserDto(
        1L,
        "admin@test.com",
        "Doe",
        "John",
        true,
        "test!1234",
        createdAt,
        updatedAt);

    assertEquals(1L, dto.getId());
    assertEquals("admin@test.com", dto.getEmail());

    assertEquals("John", dto.getFirstName());
    assertEquals("Doe", dto.getLastName());

    assertEquals("test!1234", dto.getPassword());

    assertTrue(dto.isAdmin());

    assertEquals(createdAt, dto.getCreatedAt());
    assertEquals(updatedAt, dto.getUpdatedAt());
  }

  @Test
  void shouldCreateUserDtoWithNoArgsConstructor() {

    UserDto dto = new UserDto();

    dto.setId(1L);
    dto.setEmail("admin@test.com");
    dto.setFirstName("John");
    dto.setLastName("Doe");
    dto.setAdmin(true);

    assertEquals(1L, dto.getId());
    assertEquals("admin@test.com", dto.getEmail());
    assertTrue(dto.isAdmin());
  }

  @Test
  void shouldBeEqualWhenSameValues() {

    UserDto user1 = new UserDto();
    user1.setId(1L);
    user1.setEmail("test@test.com");
    user1.setFirstName("John");
    user1.setLastName("Doe");

    UserDto user2 = new UserDto();
    user2.setId(1L);
    user2.setEmail("test@test.com");
    user2.setFirstName("John");
    user2.setLastName("Doe");

    assertEquals(user1, user2);
  }

  @Test
  void shouldNotBeEqualWhenDifferentValues() {

    UserDto user1 = new UserDto();
    user1.setId(1L);

    UserDto user2 = new UserDto();
    user2.setId(2L);

    assertNotEquals(user1, user2);
  }

  @Test
  void shouldTestEqualsFully() {

    UserDto u1 = new UserDto();
    UserDto u2 = new UserDto();

    assertEquals(u1, u1);
    assertNotEquals(null, u1);
    assertNotEquals("string", u1);

    u1.setId(1L);
    u2.setId(1L);

    assertEquals(u1, u2);

    u2.setId(2L);

    assertNotEquals(u1, u2);
  }

  @Test
  void shouldGenerateToString() {
    UserDto dto = new UserDto();
    dto.setId(1L);

    String result = dto.toString();

    assertNotNull(result);
    assertFalse(result.isEmpty());
  }

  @Test
  void shouldNotBeEqualToDifferentType() {
    UserDto dto = new UserDto();
    assertNotEquals("test", dto);
  }

  @Test
  void shouldBeEqualWhenAllFieldsMatch() {
    LocalDateTime now = LocalDateTime.now();

    UserDto dto1 = new UserDto(
        1L,
        "Doe",
        "John",
        "john@test.com",
        true,
        "password",
        now,
        now);

    UserDto dto2 = new UserDto(
        1L,
        "Doe",
        "John",
        "john@test.com",
        true,
        "password",
        now,
        now);

    assertEquals(dto1, dto2);
  }

}
