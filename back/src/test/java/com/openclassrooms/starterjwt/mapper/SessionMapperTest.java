package com.openclassrooms.starterjwt.mapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.TeacherService;
import com.openclassrooms.starterjwt.services.UserService;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class SessionMapperTest {

  @Mock
  private UserService userService;

  @Mock
  private TeacherService teacherService;

  @InjectMocks
  private SessionMapper mapper = Mappers.getMapper(SessionMapper.class);

  // =========================
  // DTO -> ENTITY
  // =========================
  @Test
  void shouldMapDtoToEntity() {

    SessionDto dto = new SessionDto();
    dto.setId(1L);
    dto.setName("Yoga");
    dto.setDescription("Cours");
    dto.setTeacher_id(10L);
    dto.setUsers(Arrays.asList(1L, 2L));

    Teacher teacher = new Teacher();
    teacher.setId(10L);

    User user1 = new User();
    user1.setId(1L);

    User user2 = new User();
    user2.setId(2L);

    when(teacherService.findById(10L)).thenReturn(teacher);
    when(userService.findById(1L)).thenReturn(user1);
    when(userService.findById(2L)).thenReturn(user2);

    Session session = mapper.toEntity(dto);

    assertNotNull(session);
    assertEquals(dto.getId(), session.getId());
    assertEquals(dto.getName(), session.getName());
    assertEquals(dto.getDescription(), session.getDescription());

    assertEquals(teacher, session.getTeacher());
    assertEquals(2, session.getUsers().size());

    verify(teacherService).findById(10L);
    verify(userService).findById(1L);
    verify(userService).findById(2L);
  }

  // =========================
  // ENTITY -> DTO
  // =========================
  @Test
  void shouldMapEntityToDto() {

    Teacher teacher = new Teacher();
    teacher.setId(10L);

    User user = new User();
    user.setId(1L);

    Session session = new Session();
    session.setId(1L);
    session.setName("Yoga");
    session.setDescription("Cours");
    session.setTeacher(teacher);
    session.setUsers(Arrays.asList(user));

    SessionDto dto = mapper.toDto(session);

    assertNotNull(dto);
    assertEquals(session.getId(), dto.getId());
    assertEquals(session.getName(), dto.getName());
    assertEquals(session.getDescription(), dto.getDescription());

    assertEquals(10L, dto.getTeacher_id());
    assertEquals(1, dto.getUsers().size());
    assertEquals(1L, dto.getUsers().get(0));
  }

  // =========================
  // LIST DTO -> ENTITY
  // =========================
  @Test
  void shouldMapSessionDtoListToEntityList() {

    SessionDto dto = new SessionDto();
    dto.setId(1L);
    dto.setUsers(new ArrayList<>());

    when(teacherService.findById(any())).thenReturn(new Teacher());

    List<Session> result = mapper.toEntity(Arrays.asList(dto));

    assertNotNull(result);
    assertEquals(1, result.size());
  }

  // =========================
  // LIST ENTITY -> DTO
  // =========================
  @Test
  void shouldMapSessionEntityListToDtoList() {

    Teacher teacher = new Teacher();
    teacher.setId(1L);

    Session session = new Session();
    session.setId(1L);
    session.setTeacher(teacher);
    session.setUsers(new ArrayList<>());

    List<SessionDto> result = mapper.toDto(Arrays.asList(session));

    assertNotNull(result);
    assertEquals(1, result.size());
  }

  // =========================
  // EDGE CASES
  // =========================
  @Test
  void shouldHandleNullTeacher() {

    Session session = new Session();
    session.setTeacher(null);

    SessionDto dto = mapper.toDto(session);

    assertNotNull(dto);
    assertNull(dto.getTeacher_id());
  }

  // @Test
  // void shouldHandleNullUsersList() {

  // Session session = new Session();
  // session.setUsers(null);

  // SessionDto dto = mapper.toDto(session);

  // assertNotNull(dto);
  // assertTrue(dto.getUsers().isEmpty());
  // }

  // @Test
  // void shouldHandleNullUsersInDto() {

  // SessionDto dto = new SessionDto();
  // dto.setUsers(null);

  // when(teacherService.findById(any())).thenReturn(new Teacher());

  // Session session = mapper.toEntity(dto);

  // assertNotNull(session);
  // assertTrue(session.getUsers().isEmpty());
  // }

  // =============$$$$$$$$$$$$$$$$$$$$$

  // @Test
  // void shouldCoverSessionTeacherIdWithNullSession() throws Exception {

  // Method method = SessionMapperImpl.class
  // .getDeclaredMethod("sessionTeacherId", Session.class);

  // method.setAccessible(true);

  // Long result = (Long) method.invoke(mapper, (Session) null);

  // assertNull(result);
  // }

}
