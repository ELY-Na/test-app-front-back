package com.openclassrooms.starterjwt.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.services.SessionService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class SessionControllerTest {

  // Mock du service (on ne veut pas toucher la vraie DB)
  @Mock
  private SessionService sessionService;

  // Mock du mapper (DTO <-> Entity)
  @Mock
  private SessionMapper sessionMapper;

  // Controller testé (on injecte les mocks dedans)
  @InjectMocks
  private SessionController sessionController;

  /**
   * TEST : GET /api/session/{id}
   * Cas nominal : la session existe
   */
  @Test
  void shouldFindSessionById() {

    // --- GIVEN (préparation des données) ---
    Session session = new Session();
    session.setId(1L);

    SessionDto dto = new SessionDto();
    dto.setId(1L);

    // Mock du service
    when(sessionService.getById(1L)).thenReturn(session);

    // Mock du mapper
    when(sessionMapper.toDto(session)).thenReturn(dto);

    // --- WHEN (appel du controller) ---
    ResponseEntity<?> response = sessionController.findById("1");

    // --- THEN (vérifications) ---
    assertEquals(200, response.getStatusCodeValue());
    assertEquals(dto, response.getBody());

    verify(sessionService).getById(1L);
    verify(sessionMapper).toDto(session);
  }

  /**
   * TEST : GET /api/session/{id}
   * Cas erreur : id non numérique
   */
  @Test
  void shouldReturnBadRequestWhenIdIsInvalid() {

    ResponseEntity<?> response = sessionController.findById("abc");

    assertEquals(400, response.getStatusCodeValue());

    verifyNoInteractions(sessionService);
  }

  /**
   * TEST : GET /api/session/{id}
   * Cas : session introuvable
   */
  @Test
  void shouldReturnNotFoundWhenSessionDoesNotExist() {

    when(sessionService.getById(1L)).thenReturn(null);

    ResponseEntity<?> response = sessionController.findById("1");

    assertEquals(404, response.getStatusCodeValue());

    verify(sessionService).getById(1L);
  }

  /**
   * TEST : GET /api/session
   */
  @Test
  void shouldFindAllSessions() {

    Session session1 = new Session();
    session1.setId(1L);

    Session session2 = new Session();
    session2.setId(2L);

    List<Session> sessions = Arrays.asList(session1, session2);

    SessionDto dto1 = new SessionDto();
    dto1.setId(1L);

    SessionDto dto2 = new SessionDto();
    dto2.setId(2L);

    when(sessionService.findAll()).thenReturn(sessions);
    when(sessionMapper.toDto(sessions)).thenReturn(Arrays.asList(dto1, dto2));

    ResponseEntity<?> response = sessionController.findAll();

    assertEquals(200, response.getStatusCodeValue());
    // assertEquals(2, ((List<?>) response.getBody()).size());
    List<?> body = (List<?>) response.getBody();

    assertEquals(2, body.size());
    assertEquals(dto1, body.get(0));
    assertEquals(dto2, body.get(1));
  }

  /**
   * TEST : POST /api/session
   */
  @Test
  void shouldCreateSession() {

    SessionDto inputDto = new SessionDto();
    Session session = new Session();
    Session savedSession = new Session();
    SessionDto outputDto = new SessionDto();

    when(sessionMapper.toEntity(inputDto)).thenReturn(session);
    when(sessionService.create(session)).thenReturn(savedSession);
    when(sessionMapper.toDto(savedSession)).thenReturn(outputDto);

    ResponseEntity<?> response = sessionController.create(inputDto);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(outputDto, response.getBody());

    verify(sessionMapper).toEntity(inputDto);
    verify(sessionService).create(session);
    verify(sessionMapper).toDto(savedSession);
  }

  /**
   * TEST : DELETE /api/session/{id}
   */
  @Test
  void shouldDeleteSession() {

    Session session = new Session();
    session.setId(1L);

    when(sessionService.getById(1L)).thenReturn(session);

    ResponseEntity<?> response = sessionController.save("1");

    assertEquals(200, response.getStatusCodeValue());

    verify(sessionService).delete(1L);
  }

  /**
   * TEST : POST /participate
   */
  @Test
  void shouldParticipate() {

    doNothing().when(sessionService).participate(1L, 2L);

    ResponseEntity<?> response = sessionController.participate("1", "2");

    assertEquals(200, response.getStatusCodeValue());

    verify(sessionService).participate(1L, 2L);
  }

  /**
   * TEST : DELETE /participate
   */
  @Test
  void shouldNoLongerParticipate() {

    doNothing().when(sessionService).noLongerParticipate(1L, 2L);

    ResponseEntity<?> response = sessionController.noLongerParticipate("1", "2");

    assertEquals(200, response.getStatusCodeValue());

    verify(sessionService).noLongerParticipate(1L, 2L);
  }

  @Test
  void shouldReturnBadRequestWhenUpdateIdIsInvalid() {

    // GIVEN
    SessionDto dto = new SessionDto();

    // WHEN
    ResponseEntity<?> response = sessionController.update("abc", dto);

    // THEN
    assertEquals(400, response.getStatusCodeValue());

    verifyNoInteractions(sessionService);
  }

  @Test
  void shouldUpdateSession() {

    SessionDto dto = new SessionDto();
    dto.setName("Yoga");

    Session entity = new Session();
    entity.setId(1L);

    when(sessionMapper.toEntity(dto)).thenReturn(entity);
    when(sessionService.update(1L, entity)).thenReturn(entity);
    when(sessionMapper.toDto(entity)).thenReturn(dto);

    ResponseEntity<?> response = sessionController.update("1", dto);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(dto, response.getBody());

    verify(sessionMapper).toEntity(dto);
    verify(sessionService).update(1L, entity);
    verify(sessionMapper).toDto(entity);
  }

  @Test
  void shouldReturnBadRequestWhenDeleteIdIsInvalid() {

    ResponseEntity<?> response = sessionController.save("abc");

    assertEquals(400, response.getStatusCodeValue());

    verifyNoInteractions(sessionService);
  }

  @Test
  void shouldReturnBadRequestWhenParticipateIdIsInvalid() {

    ResponseEntity<?> response = sessionController.participate("abc", "1");

    assertEquals(400, response.getStatusCodeValue());

    verifyNoInteractions(sessionService);
  }

  @Test
  void shouldReturnBadRequestWhenNoLongerParticipateIdIsInvalid() {

    ResponseEntity<?> response = sessionController.noLongerParticipate("abc", "1");

    assertEquals(400, response.getStatusCodeValue());

    verifyNoInteractions(sessionService);
  }

  @Test
  void shouldReturnBadRequestWhenNoLongerParticipateUserIdIsInvalid() {

    ResponseEntity<?> response = sessionController.noLongerParticipate("1", "abc");

    assertEquals(400, response.getStatusCodeValue());

    verifyNoInteractions(sessionService);
  }

  @Test
  void shouldReturnNotFoundWhenDeletingUnknownSession() {

    when(sessionService.getById(1L))
        .thenReturn(null);

    ResponseEntity<?> response = sessionController.save("1");

    assertEquals(404, response.getStatusCodeValue());

    verify(sessionService).getById(1L);
  }

}
