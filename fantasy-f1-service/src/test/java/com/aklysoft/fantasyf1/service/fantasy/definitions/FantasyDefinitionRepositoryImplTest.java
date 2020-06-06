package com.aklysoft.fantasyf1.service.fantasy.definitions;

import com.aklysoft.fantasyf1.service.fantasy.definitions.series.FantasySeriesType;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@QuarkusTest
@Tag("integration")
class FantasyDefinitionRepositoryImplTest {

  @Inject
  FantasyDefinitionRepositoryImpl fantasyDefinitionRepository;

  @PersistenceContext
  EntityManager em;

  final String series = FantasySeriesType.FORMULA_1.id;
  final int season = 2019;
  final int nextRace = 3;

  @BeforeEach
  @Transactional
  void setup() {
    em.createQuery("delete from FantasyDefinition").executeUpdate();

    Stream.of(
            FantasyDefinition.builder().series(FantasySeriesType.FORMULA_1.id).season(2018).build(),
            FantasyDefinition.builder().series(FantasySeriesType.FORMULA_1.id).season(season).nextRace(nextRace).build(),
            FantasyDefinition.builder().series(FantasySeriesType.FORMULA_E.id).season(season).build())
            .forEach(fantasyDefinition -> em.persist(fantasyDefinition));

  }

  @Test
  public void shouldGetCurrentSeason() {
    assertEquals(season, fantasyDefinitionRepository.getCurrentSeason(series));
  }

  @Test
  public void shouldGetNextRace() {
    assertEquals(nextRace, fantasyDefinitionRepository.getNextRace(series, season));
  }

  @Test
  public void shouldGetNullOnGetNextRaceWhenSeasonIsInvalid() {
    assertNull(fantasyDefinitionRepository.getNextRace(series, 2010));
  }

  @Test
  public void shouldGetSeasons() {
    assertEquals(List.of(2018, 2019), fantasyDefinitionRepository.getSeasons(series));
  }


}
