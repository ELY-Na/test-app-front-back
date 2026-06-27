package com.openclassrooms.starterjwt.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.models.User;

class UserMapperTest {

  private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

  @Test
  void shouldMapUserToDto() {

    User user = new User();
    user.setId(1L);
    user.setEmail("admin@test.com");
    user.setFirstName("John");
    user.setLastName("Doe");
    user.setAdmin(true);

    UserDto dto = mapper.toDto(user);

    assertEquals(user.getId(), dto.getId());
    assertEquals(user.getEmail(), dto.getEmail());
    assertEquals(user.getFirstName(), dto.getFirstName());
    assertEquals(user.getLastName(), dto.getLastName());
    assertEquals(user.isAdmin(), dto.isAdmin());
  }

  @Test
  void shouldMapDtoToUser() {

    UserDto dto = new UserDto();
    dto.setId(1L);
    dto.setEmail("admin@test.com");
    dto.setPassword("test!1234");
    dto.setFirstName("John");
    dto.setLastName("Doe");
    dto.setAdmin(true);

    User user = mapper.toEntity(dto);

    assertEquals(dto.getId(), user.getId());
    assertEquals(dto.getEmail(), user.getEmail());
    assertEquals(dto.getFirstName(), user.getFirstName());
    assertEquals(dto.getLastName(), user.getLastName());
    assertEquals(dto.isAdmin(), user.isAdmin());
  }

  @Test
  void shouldMapUserDtoListToEntityList() {

    UserDto dto = new UserDto();
    dto.setId(1L);
    dto.setEmail("test@test.com");
    dto.setPassword("password");
    dto.setFirstName("John");
    dto.setLastName("Doe");

    List<UserDto> dtoList = Arrays.asList(dto);

    List<User> users = mapper.toEntity(dtoList);

    assertNotNull(users);
    assertEquals(1, users.size());
    assertEquals(1L, users.get(0).getId());
  }

  @Test
  void shouldMapUserEntityListToDtoList() {

    User user = new User();
    user.setId(1L);
    user.setEmail("test@test.com");
    user.setPassword("password");
    user.setFirstName("John");
    user.setLastName("Doe");

    List<User> users = Arrays.asList(user);

    List<UserDto> dtos = mapper.toDto(users);

    assertNotNull(dtos);
    assertEquals(1, dtos.size());
    assertEquals(1L, dtos.get(0).getId());
  }

  @Test
  void shouldReturnNullWhenUserDtoListIsNull() {
    assertNull(mapper.toEntity((List<UserDto>) null));
  }

  @Test
  void shouldReturnNullWhenUserEntityListIsNull() {
    assertNull(mapper.toDto((List<User>) null));
  }
}