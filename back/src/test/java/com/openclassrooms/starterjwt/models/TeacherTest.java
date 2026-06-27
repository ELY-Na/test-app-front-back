package com.openclassrooms.starterjwt.models;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TeacherTest {

  @Test
  void shouldEqualWhenSameId() {

    Teacher t1 = new Teacher();
    t1.setId(1L);

    Teacher t2 = new Teacher();
    t2.setId(1L);

    assertEquals(t1, t2);
  }

  @Test
  void shouldNotEqualWhenDifferentId() {

    Teacher t1 = new Teacher();
    t1.setId(1L);

    Teacher t2 = new Teacher();
    t2.setId(2L);

    assertNotEquals(t1, t2);
  }
}