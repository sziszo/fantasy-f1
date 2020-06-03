package com.aklysoft.fantasyf1.service.fantasy.teams;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@Tag("integration")
class FantasyTeamRepositoryImplTest {

  @Inject
  FantasyTeamRepositoryImpl fantasyTeamRepository;

  @PersistenceContext
  EntityManager em;

  private String series = "f1";
  private int season = 2019;

  private String user1 = "user";
  private String user1TeamName = "UserF1Team";
  private final FantasyTeam fantasyTeam1 = FantasyTeam.builder().series(series).season(season).userName(user1).name(user1TeamName).build();

  private String user2 = "john";
  private String user2TeamName = "JohnF1Team";
  private final FantasyTeam fantasyTeam2 = FantasyTeam.builder().series(series).season(season).userName(user2).name(user2TeamName).build();


  @BeforeEach
  @Transactional
  public void setupDb() {
    em.createQuery("delete from FantasyTeam").executeUpdate();

    em.persist(fantasyTeam1);
    em.persist(fantasyTeam2);
    em.persist(FantasyTeam.builder().series(series).season(2018).userName(user1).name("OldUserF1Team").build());

  }

  @Test
  public void shouldFindAllBySeriesAndSeason() {

    //when
    final List<FantasyTeam> result = fantasyTeamRepository.findAllBySeriesAndSeason(series, season);

    //then
    assertNotNull(result);
    assertEquals(2, result.size());
  }

  @Test
  public void shouldNameExists() {
    assertTrue(fantasyTeamRepository.isExistName(user1TeamName, user2));
  }

  @Test
  public void shouldNameNotExists() {
    assertFalse(fantasyTeamRepository.isExistName(user1TeamName, user1));
  }
}
