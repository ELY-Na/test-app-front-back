package com.openclassrooms.starterjwt.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Teacher;

class TeacherMapperTest {

  private final TeacherMapper mapper = Mappers.getMapper(TeacherMapper.class);

  @Test
  void shouldMapTeacherToDto() {

    Teacher teacher = new Teacher();
    teacher.setId(1L);
    teacher.setFirstName("John");
    teacher.setLastName("Doe");

    TeacherDto dto = mapper.toDto(teacher);

    assertEquals(1L, dto.getId());
    assertEquals("John", dto.getFirstName());
    assertEquals("Doe", dto.getLastName());
  }

  @Test
  void shouldMapDtoToTeacher() {

    TeacherDto dto = new TeacherDto();
    dto.setId(1L);
    dto.setFirstName("John");
    dto.setLastName("Doe");

    Teacher teacher = mapper.toEntity(dto);

    assertEquals(1L, teacher.getId());
    assertEquals("John", teacher.getFirstName());
    assertEquals("Doe", teacher.getLastName());
  }

  @Test
  void shouldMapTeacherListToDtoList() {

    Teacher teacher = new Teacher();
    teacher.setId(1L);
    teacher.setFirstName("John");
    teacher.setLastName("Doe");

    List<Teacher> teachers = Arrays.asList(teacher);

    List<TeacherDto> result = mapper.toDto(teachers);

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals(1L, result.get(0).getId());
    assertEquals("John", result.get(0).getFirstName());
    assertEquals("Doe", result.get(0).getLastName());
  }

  @Test
  void shouldMapTeacherDtoListToEntityList() {

    TeacherDto dto = new TeacherDto();
    dto.setId(1L);
    dto.setFirstName("John");
    dto.setLastName("Doe");

    List<TeacherDto> dtos = Arrays.asList(dto);

    List<Teacher> result = mapper.toEntity(dtos);

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals(1L, result.get(0).getId());
    assertEquals("John", result.get(0).getFirstName());
    assertEquals("Doe", result.get(0).getLastName());
  }

  @Test
  void shouldReturnNullWhenTeacherDtoListIsNull() {
    assertNull(mapper.toEntity((List<TeacherDto>) null));
  }

  @Test
  void shouldReturnNullWhenTeacherListIsNull() {
    assertNull(mapper.toDto((List<Teacher>) null));
  }
}