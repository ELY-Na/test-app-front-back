package com.openclassrooms.starterjwt.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {

  @Mock
  TeacherRepository teacherRepository;

  @InjectMocks
  private TeacherService teacherService;

  @Test
  void shouldFindById() {
    Teacher teacher = new Teacher();
    teacher.setId(1L);

    when(teacherRepository.findById(1L))
        .thenReturn(Optional.of(teacher));

    Teacher result = teacherService.findById(1L);

    assertNotNull(result);
    assertEquals(1L, result.getId());

    verify(teacherRepository, times(1)).findById(1L);

  }

  @Test
  void shouldFindAllTeachers() {

    Teacher teacher1 = new Teacher();
    teacher1.setId(1L);

    Teacher teacher2 = new Teacher();
    teacher2.setId(2L);

    List<Teacher> teachers = Arrays.asList(teacher1, teacher2);

    when(teacherRepository.findAll())
        .thenReturn(teachers);

    List<Teacher> result = teacherService.findAll();

    assertEquals(2, result.size());
    assertEquals(1L, result.get(0).getId());

    verify(teacherRepository, times(1)).findAll();
  }
}
