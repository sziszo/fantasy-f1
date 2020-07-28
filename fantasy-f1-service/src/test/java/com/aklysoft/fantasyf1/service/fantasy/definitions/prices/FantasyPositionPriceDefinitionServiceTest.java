package com.aklysoft.fantasyf1.service.fantasy.definitions.prices;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FantasyPositionPriceDefinitionServiceTest {

  @InjectMocks
  FantasyPositionPriceDefinitionService fantasyPositionPriceDefinitionService;

  @Mock
  FantasyPositionPriceDefinitionRepository fantasyPositionPriceDefinitionRepository;

  private final String series = "f1";
  private final int season = 2019;

  @Test
  void testGetInitialDriverPositionPrices() {
    final FantasyPositionPriceDefinitionType type = FantasyPositionPriceDefinitionType.INITIAL_DRIVER_PRICE_PERCENT;

    List<FantasyPositionPriceDefinition> fantasyPositionPriceDefinitions = List.of(
            FantasyPositionPriceDefinition.builder().series(series).season(season).type(type).position(1).percent(100).build(),
            FantasyPositionPriceDefinition.builder().series(series).season(season).type(type).position(2).percent(50).build(),
            FantasyPositionPriceDefinition.builder().series(series).season(season).type(type).position(3).percent(10).build()
    );

    when(fantasyPositionPriceDefinitionRepository.getFantasyPositionPriceDefinitions(series, season, type)).thenReturn(fantasyPositionPriceDefinitions);

    assertEquals(fantasyPositionPriceDefinitions, fantasyPositionPriceDefinitionService.getInitialDriverPositionPrices(series, season));

    verify(fantasyPositionPriceDefinitionRepository).getFantasyPositionPriceDefinitions(series, season, type);
  }

  @Test
  void testGetInitialConstructorPositionPrices() {
    final FantasyPositionPriceDefinitionType type = FantasyPositionPriceDefinitionType.INITIAL_CONSTRUCTOR_PRICE_PERCENT;

    List<FantasyPositionPriceDefinition> fantasyPositionPriceDefinitions = List.of(
            FantasyPositionPriceDefinition.builder().series(series).season(season).type(type).position(1).percent(50).build(),
            FantasyPositionPriceDefinition.builder().series(series).season(season).type(type).position(2).percent(40).build(),
            FantasyPositionPriceDefinition.builder().series(series).season(season).type(type).position(3).percent(30).build()
    );

    when(fantasyPositionPriceDefinitionRepository.getFantasyPositionPriceDefinitions(series, season, type)).thenReturn(fantasyPositionPriceDefinitions);

    assertEquals(fantasyPositionPriceDefinitions, fantasyPositionPriceDefinitionService.getInitialConstructorPositionPrices(series, season));

    verify(fantasyPositionPriceDefinitionRepository).getFantasyPositionPriceDefinitions(series, season, type);
  }

  @Test
  void testGetRaceDriverPositionPrices() {
    final FantasyPositionPriceDefinitionType type = FantasyPositionPriceDefinitionType.RACE_DRIVER_PRICE_PERCENT;

    List<FantasyPositionPriceDefinition> fantasyPositionPriceDefinitions = List.of(
            FantasyPositionPriceDefinition.builder().series(series).season(season).type(type).position(1).percent(100).build(),
            FantasyPositionPriceDefinition.builder().series(series).season(season).type(type).position(2).percent(50).build(),
            FantasyPositionPriceDefinition.builder().series(series).season(season).type(type).position(3).percent(10).build()
    );

    when(fantasyPositionPriceDefinitionRepository.getFantasyPositionPriceDefinitions(series, season, type)).thenReturn(fantasyPositionPriceDefinitions);

    assertEquals(fantasyPositionPriceDefinitions, fantasyPositionPriceDefinitionService.getRaceDriverPositionPrices(series, season));

    verify(fantasyPositionPriceDefinitionRepository).getFantasyPositionPriceDefinitions(series, season, type);
  }

  @Test
  void testGetRaceConstructorPositionPrices() {
    final FantasyPositionPriceDefinitionType type = FantasyPositionPriceDefinitionType.RACE_CONSTRUCTOR_PRICE_PERCENT;

    List<FantasyPositionPriceDefinition> fantasyPositionPriceDefinitions = List.of(
            FantasyPositionPriceDefinition.builder().series(series).season(season).type(type).position(1).percent(50).build(),
            FantasyPositionPriceDefinition.builder().series(series).season(season).type(type).position(2).percent(40).build(),
            FantasyPositionPriceDefinition.builder().series(series).season(season).type(type).position(3).percent(30).build()
    );

    when(fantasyPositionPriceDefinitionRepository.getFantasyPositionPriceDefinitions(series, season, type)).thenReturn(fantasyPositionPriceDefinitions);

    assertEquals(fantasyPositionPriceDefinitions, fantasyPositionPriceDefinitionService.getRaceConstructorPositionPrices(series, season));

    verify(fantasyPositionPriceDefinitionRepository).getFantasyPositionPriceDefinitions(series, season, type);
  }
}
