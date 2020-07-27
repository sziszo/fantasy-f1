package com.aklysoft.fantasyf1.service.fantasy.definitions;

import com.aklysoft.fantasyf1.service.fantasy.definitions.series.FantasySeriesType;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
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
@QuarkusTestResource(H2DatabaseTestResource.class)
@Tag("integration")
class FantasyDefinitionRepositoryImplTest {

  @Inject
  FantasyDefinitionRepositoryImpl fantasyDefinitionRepository;

  @PersistenceContext
  EntityManager em;

  final String series = FantasySeriesType.FORMULA_1.id;
  final int season = 2019;
  final int nextRace = 3;
  final long initialMoney = 1_000_000;
  final int initialDriverPrice = 15_000_000;
  final int initialConstructorPrice = 10_000_000;
  private String invalidSeries = "ff";
  private final int invalidSeason = 2010;

  @BeforeEach
  @Transactional
  void setup() {
    em.createQuery("delete from FantasyDefinition").executeUpdate();

    Stream.of(
            FantasyDefinition.builder()
                    .series(FantasySeriesType.FORMULA_1.id)
                    .season(2018)
                    .initialMoney(initialMoney)
                    .initialDriverPrice(initialDriverPrice)
                    .initialConstructorPrice(initialConstructorPrice)
                    .build(),
            FantasyDefinition.builder()
                    .series(FantasySeriesType.FORMULA_1.id)
                    .season(season)
                    .nextRace(nextRace)
                    .initialMoney(initialMoney)
                    .initialDriverPrice(initialDriverPrice)
                    .initialConstructorPrice(initialConstructorPrice)
                    .build(),
            FantasyDefinition.builder()
                    .series(FantasySeriesType.FORMULA_E.id)
                    .season(season)
                    .initialMoney(initialMoney)
                    .initialDriverPrice(initialDriverPrice)
                    .initialConstructorPrice(initialConstructorPrice).build())
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

    assertNull(fantasyDefinitionRepository.getNextRace(series, invalidSeason));
  }

  @Test
  public void shouldGetSeasons() {
    assertEquals(List.of(2018, 2019), fantasyDefinitionRepository.getSeasons(series));
  }

  @Test
  public void shouldGetInitialMoney() {
    assertEquals(initialMoney, fantasyDefinitionRepository.getInitialMoney(series, season));
  }

  @Test
  public void shouldGetNullOnGetInitialMoneyWhenSeasonIsInvalid() {
    assertNull(fantasyDefinitionRepository.getInitialMoney(series, invalidSeason));
  }

  @Test
  public void shouldGetNullOnGetInitialMoneyWhenSeriesIsInvalid() {
    assertNull(fantasyDefinitionRepository.getInitialMoney(invalidSeries, season));
  }

  @Test
  public void shouldGetInitialDriverPrice() {
    assertEquals(initialDriverPrice, fantasyDefinitionRepository.getInitialDriverPrice(series, season));
  }

  @Test
  public void shouldGetNullOnGetInitialDriverPriceWhenSeasonIsInvalid() {
    assertNull(fantasyDefinitionRepository.getInitialDriverPrice(series, invalidSeason));
  }

  @Test
  public void shouldGetNullOnGetInitialDriverPriceWhenSeriesIsInvalid() {
    assertNull(fantasyDefinitionRepository.getInitialDriverPrice(invalidSeries, season));
  }

  @Test
  public void shouldGetInitialConstructorPrice() {
    assertEquals(initialConstructorPrice, fantasyDefinitionRepository.getInitialConstructorPrice(series, season));
  }

  @Test
  public void shouldGetNullOnGetInitialConstructorPriceWhenSeasonIsInvalid() {
    assertNull(fantasyDefinitionRepository.getInitialConstructorPrice(series, invalidSeason));
  }

  @Test
  public void shouldGetNullOnGetInitialConstructorPriceWhenSeriesIsInvalid() {
    assertNull(fantasyDefinitionRepository.getInitialConstructorPrice(invalidSeries, season));
  }

}
