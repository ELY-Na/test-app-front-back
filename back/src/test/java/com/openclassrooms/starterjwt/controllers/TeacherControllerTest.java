package com.openclassrooms.starterjwt.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class TeacherControllerTest {

  // Mock du service (pas de vraie DB)
  @Mock
  private TeacherService teacherService;

  // Mock du mapper (Entity <-> DTO)
  @Mock
  private TeacherMapper teacherMapper;

  // Controller testé
  @InjectMocks
  private TeacherController teacherController;

  @Test
  void shouldFindTeacherById() {
    Teacher teacher = new Teacher();
    teacher.setId(1L);

    TeacherDto dto = new TeacherDto();
    dto.setId(1L);

    when(teacherService.findById(1L)).thenReturn(teacher);
    when(teacherMapper.toDto(teacher)).thenReturn(dto);

    ResponseEntity<?> response = teacherController.findById("1");

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(dto, response.getBody());

    verify(teacherService).findById(1L);
    verify(teacherMapper).toDto(teacher);
  }

  @Test
  void shouldFindAllTeachers() {
    Teacher t1 = new Teacher();
    t1.setId(1L);

    Teacher t2 = new Teacher();
    t2.setId(2L);

    TeacherDto dto1 = new TeacherDto();
    dto1.setId(1L);

    TeacherDto dto2 = new TeacherDto();
    dto2.setId(2L);

    when(teacherService.findAll()).thenReturn(Arrays.asList(t1, t2));
    when(teacherMapper.toDto(Arrays.asList(t1, t2)))
        .thenReturn(Arrays.asList(dto1, dto2));

    ResponseEntity<?> response = teacherController.findAll();

    assertEquals(200, response.getStatusCodeValue());

    List<?> body = (List<?>) response.getBody();
    assertEquals(dto1, body.get(0));
    assertEquals(dto2, body.get(1));
  }
}
