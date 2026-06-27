package com.openclassrooms.starterjwt.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SessionServiceTest {

  @Mock
  private SessionRepository sessionRepository;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private SessionService sessionService;

  /**
   * Vérifie que create() appelle bien save()
   * et retourne la session sauvegardée.
   */
  @Test
  void shouldCreateSession() {

    Session session = new Session();
    session.setId(1L);

    when(sessionRepository.save(session))
        .thenReturn(session);

    Session result = sessionService.create(session);

    assertNotNull(result);
    assertEquals(1L, result.getId());

    verify(sessionRepository, times(1)).save(session);
  }

  /**
   * Vérifie que findAll() retourne
   * toutes les sessions du repository.
   */
  @Test
  void shouldFindAllSessions() {

    Session session1 = new Session();
    session1.setId(1L);

    Session session2 = new Session();
    session2.setId(2L);

    List<Session> sessions = Arrays.asList(session1, session2);

    when(sessionRepository.findAll())
        .thenReturn(sessions);

    List<Session> result = sessionService.findAll();

    assertNotNull(result);
    assertEquals(2, result.size());

    verify(sessionRepository, times(1)).findAll();
  }

  /**
   * Vérifie que getById()
   * retourne la session demandée.
   */
  @Test
  void shouldGetSessionById() {

    Session session = new Session();
    session.setId(1L);

    when(sessionRepository.findById(1L))
        .thenReturn(Optional.of(session));

    Session result = sessionService.getById(1L);

    assertNotNull(result);
    assertEquals(1L, result.getId());

    verify(sessionRepository, times(1)).findById(1L);
  }

  @Test
  void shouldThrowBadRequestWhenUserAlreadyParticipates() {

    User user = new User();
    user.setId(2L);

    Session session = new Session();
    session.setId(1L);
    session.setUsers(new ArrayList<>());

    // l'utilisateur participe déjà
    session.getUsers().add(user);

    when(sessionRepository.findById(1L))
        .thenReturn(Optional.of(session));

    when(userRepository.findById(2L))
        .thenReturn(Optional.of(user));

    assertThrows(
        BadRequestException.class,
        () -> sessionService.participate(1L, 2L));

    verify(sessionRepository, never()).save(any());
  }

  @Test
  void shouldThrowNotFoundWhenSessionDoesNotExist() {

    when(sessionRepository.findById(1L))
        .thenReturn(Optional.empty());

    assertThrows(
        NotFoundException.class,
        () -> sessionService.participate(1L, 2L));

    verify(sessionRepository, never()).save(any());
  }

  @Test
  void shouldThrowNotFoundWhenUserDoesNotExist() {

    Session session = new Session();
    session.setId(1L);
    session.setUsers(new ArrayList<>());

    when(sessionRepository.findById(1L))
        .thenReturn(Optional.of(session));

    when(userRepository.findById(2L))
        .thenReturn(Optional.empty());

    assertThrows(
        NotFoundException.class,
        () -> sessionService.participate(1L, 2L));

    verify(sessionRepository, never()).save(any());
  }

  @Test
  void shouldParticipate() {

    User user = new User();
    user.setId(2L);

    Session session = new Session();
    session.setId(1L);
    session.setUsers(new ArrayList<>());

    when(sessionRepository.findById(1L))
        .thenReturn(Optional.of(session));

    when(userRepository.findById(2L))
        .thenReturn(Optional.of(user));

    // appel de la méthode
    sessionService.participate(1L, 2L);

    // l'utilisateur a été ajouté
    assertEquals(1, session.getUsers().size());
    assertEquals(user, session.getUsers().get(0));

    // la session a été sauvegardée
    verify(sessionRepository).save(session);
  }

  /**
   * Vérifie que getById()
   * retourne null si la session n'existe pas.
   */
  @Test
  void shouldReturnNullWhenSessionNotFound() {

    when(sessionRepository.findById(1L))
        .thenReturn(Optional.empty());

    Session result = sessionService.getById(1L);

    assertNull(result);

    verify(sessionRepository, times(1)).findById(1L);
  }

  /**
   * Vérifie que delete()
   * appelle bien deleteById().
   */
  @Test
  void shouldDeleteSession() {

    sessionService.delete(1L);

    verify(sessionRepository, times(1)).deleteById(1L);
  }

  /**
   * Vérifie que update()
   * remplace l'id puis sauvegarde.
   */
  @Test
  void shouldUpdateSession() {

    Session session = new Session();

    when(sessionRepository.save(any(Session.class)))
        .thenReturn(session);

    Session result = sessionService.update(1L, session);

    assertNotNull(result);
    assertEquals(1L, session.getId());

    verify(sessionRepository, times(1)).save(session);
  }

  @Test
  void shouldThrowBadRequestWhenUserNotParticipating() {

    User user = new User();
    user.setId(3L);

    Session session = new Session();
    session.setId(1L);
    session.setUsers(new ArrayList<>());

    when(sessionRepository.findById(1L))
        .thenReturn(Optional.of(session));

    assertThrows(
        BadRequestException.class,
        () -> sessionService.noLongerParticipate(1L, 3L));

    verify(sessionRepository, never()).save(any());
  }

  @Test
  void shouldThrowNotFoundWhenRemovingFromUnknownSession() {

    when(sessionRepository.findById(1L))
        .thenReturn(Optional.empty());

    assertThrows(
        NotFoundException.class,
        () -> sessionService.noLongerParticipate(1L, 2L));

    verify(sessionRepository, never()).save(any());
  }

  @Test
  void shouldRemoveUserFromSession() {

    User user1 = new User();
    user1.setId(1L);

    User user2 = new User();
    user2.setId(2L);

    Session session = new Session();
    session.setId(10L);
    session.setUsers(new ArrayList<>(Arrays.asList(user1, user2)));

    when(sessionRepository.findById(10L))
        .thenReturn(Optional.of(session));

    sessionService.noLongerParticipate(10L, 1L);

    assertEquals(1, session.getUsers().size());
    assertEquals(2L, session.getUsers().get(0).getId());

    verify(sessionRepository).save(session);
  }
}