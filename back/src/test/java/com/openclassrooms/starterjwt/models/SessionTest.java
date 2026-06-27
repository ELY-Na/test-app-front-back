package com.openclassrooms.starterjwt.models;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

class SessionTest {

  @Test
  void shouldCreateSessionWithAllArgsConstructor() {

    Date date = new Date();

    LocalDateTime createdAt = LocalDateTime.of(2026, 6, 23, 10, 30);
    LocalDateTime updatedAt = LocalDateTime.of(2026, 6, 23, 11, 0);

    Teacher teacher = new Teacher();
    teacher.setId(1L);

    List<User> users = Arrays.asList(new User(), new User());

    Session session = new Session(
        1L,
        "Yoga débutant",
        date,
        "desc",
        teacher,
        users,
        createdAt,
        updatedAt);

    assertEquals(1L, session.getId());
    assertEquals("Yoga débutant", session.getName());
    assertEquals(teacher, session.getTeacher());
    assertEquals(users, session.getUsers());
  }

  @Test
  void shouldUseNoArgsConstructorAndSetters() {

    Session session = new Session();

    session.setId(1L);
    session.setName("Yoga débutant");
    session.setDescription("desc");

    assertEquals(1L, session.getId());
    assertEquals("Yoga débutant", session.getName());
    assertEquals("desc", session.getDescription());
  }

  @Test
  void shouldManageUsersList() {

    User user = new User();
    user.setId(1L);

    Session session = new Session();
    session.setUsers(new ArrayList<>());

    session.getUsers().add(user);

    assertEquals(1, session.getUsers().size());
    assertEquals(user, session.getUsers().get(0));
  }

  @Test
  void shouldTestEqualsAndHashCode() {

    Session s1 = new Session();
    s1.setId(1L);

    Session s2 = new Session();
    s2.setId(1L);

    Session s3 = new Session();
    s3.setId(2L);

    assertEquals(s1, s2);
    assertNotEquals(s1, s3);

    assertEquals(s1.hashCode(), s2.hashCode());
  }

  @Test
  void shouldBuildSession() {

    Teacher teacher = new Teacher();
    teacher.setId(1L);

    Session session = Session.builder()
        .id(1L)
        .name("Yoga")
        .teacher(teacher)
        .build();

    assertEquals(1L, session.getId());
    assertEquals("Yoga", session.getName());
    assertEquals(teacher, session.getTeacher());
  }
}