package com.aklysoft.fantasyf1.service.fantasy.definitions;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@Tag("integration")
class FantasyDefinitionRepositoryImplTest {

  @Inject
  FantasyDefinitionRepositoryImpl fantasyDefinitionRepository;

  @PersistenceContext
  EntityManager em;

  final String series = "f1";
  final int season = 2019;

  @Test
  public void shouldGetCurrentSeason() {
    assertEquals(season, fantasyDefinitionRepository.getCurrentSeason(series));
  }

  @Test
  public void shouldGetNextRace() {
    assertEquals(1, fantasyDefinitionRepository.getNextRace(series, season));
  }

}
