package com.aklysoft.fantasyf1.service.fantasy.definitions.prices;

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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
@Tag("integration")
class FantasyPositionPriceDefinitionRepositoryImplTest {

  @Inject
  FantasyPositionPriceDefinitionRepositoryImpl fantasyPositionPriceDefinitionRepository;

  @PersistenceContext
  EntityManager em;

  final String series = "f1";
  final int season = 2019;

  List<FantasyPositionPriceDefinition> initialDriverPercents = List.of(
          FantasyPositionPriceDefinition.builder().series(series).season(season).position(1).type(FantasyPositionPriceDefinitionType.INITIAL_DRIVER_PRICE_PERCENT).percent(100).build(),
          FantasyPositionPriceDefinition.builder().series(series).season(season).position(2).type(FantasyPositionPriceDefinitionType.INITIAL_DRIVER_PRICE_PERCENT).percent(50).build(),
          FantasyPositionPriceDefinition.builder().series(series).season(season).position(3).type(FantasyPositionPriceDefinitionType.INITIAL_DRIVER_PRICE_PERCENT).percent(10).build());


  @BeforeEach
  @Transactional
  void setup() {
    em.createQuery("delete from FantasyPositionPriceDefinition").executeUpdate();
    Stream.concat(
            initialDriverPercents.stream(),
            Stream.of(
                    FantasyPositionPriceDefinition.builder().series(series).season(season).position(1).type(FantasyPositionPriceDefinitionType.RACE_DRIVER_PRICE_PERCENT).percent(100).build(),
                    FantasyPositionPriceDefinition.builder().series(series).season(season).position(2).type(FantasyPositionPriceDefinitionType.RACE_DRIVER_PRICE_PERCENT).percent(50).build(),
                    FantasyPositionPriceDefinition.builder().series(series).season(season).position(3).type(FantasyPositionPriceDefinitionType.RACE_DRIVER_PRICE_PERCENT).percent(10).build())
    ).forEach(em::persist);
  }

  @Test
  void testGetFantasyPositionPriceDefinitions() {
    assertEquals(initialDriverPercents, fantasyPositionPriceDefinitionRepository.getFantasyPositionPriceDefinitions(series, season, FantasyPositionPriceDefinitionType.INITIAL_DRIVER_PRICE_PERCENT));
  }
}
