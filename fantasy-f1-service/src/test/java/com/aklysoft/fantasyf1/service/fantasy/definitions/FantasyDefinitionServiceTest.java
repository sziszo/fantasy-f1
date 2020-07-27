package com.aklysoft.fantasyf1.service.fantasy.definitions;

import com.aklysoft.fantasyf1.service.fantasy.definitions.series.FantasySeriesType;
import com.aklysoft.fantasyf1.service.fantasy.definitions.series.InvalidFantasySeriesException;
import com.aklysoft.fantasyf1.service.original.races.OriginalRace;
import com.aklysoft.fantasyf1.service.original.races.OriginalRaceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.aklysoft.fantasyf1.service.fantasy.definitions.FantasyDefinitionMappers.createFantasyRaceViewItemId;
import static com.aklysoft.fantasyf1.service.fantasy.definitions.FantasyDefinitionMappers.toFantasyRaceViewItem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FantasyDefinitionServiceTest {

  @InjectMocks
  FantasyDefinitionService fantasyDefinitionService;

  @Mock
  FantasyDefinitionRepository fantasyDefinitionRepository;

  @Mock
  OriginalRaceService originalRaceService;

  final String series = FantasySeriesType.FORMULA_1.id;
  final int season = 2019;
  final int race = 1;
  final String invalidSeries = "ff";

  @Test
  public void shouldGetCurrentSeason() {
    //given
    when(fantasyDefinitionRepository.getCurrentSeason(series)).thenReturn(season);

    //when & then
    assertEquals(season, fantasyDefinitionService.getCurrentSeason(series));
    verify(fantasyDefinitionRepository).getCurrentSeason(series);
    verify(fantasyDefinitionRepository).getCurrentSeason(anyString());
  }

  @Test
  public void shouldThrowExceptionOnGetCurrentSeasonWhenSeriesIsInvalid() {
    assertThrows(InvalidFantasySeriesException.class, () -> fantasyDefinitionService.getCurrentSeason(invalidSeries));
    verify(fantasyDefinitionRepository, never()).getCurrentSeason(anyString());
  }

  @Test
  public void shouldGetNextRace() {
    //given
    when(fantasyDefinitionRepository.getNextRace(series, season)).thenReturn(race);

    //when & then
    assertEquals(race, fantasyDefinitionService.getNextRace(series, season));
    verify(fantasyDefinitionRepository).getNextRace(anyString(), anyInt());
  }

  @Test
  public void shouldThrowExceptionWhenGetNextRaceAndSeriesIsInvalid() {
    assertThrows(InvalidFantasySeriesException.class, () -> fantasyDefinitionService.getNextRace(invalidSeries, season));
    verify(fantasyDefinitionRepository, never()).getNextRace(anyString(), anyInt());
  }

  @Test
  public void shouldThrowExceptionOnGetNextRaceWhenSeasonIsInvalid() {
    //given
    final int invalidSeason = 2010;
    when(fantasyDefinitionRepository.getNextRace(series, invalidSeason)).thenReturn(null);

    //when & then
    assertThrows(NoNextFantasyRace.class, () -> fantasyDefinitionService.getNextRace(series, invalidSeason));
    verify(fantasyDefinitionRepository).getNextRace(series, invalidSeason);
    verify(fantasyDefinitionRepository).getNextRace(anyString(), anyInt());
  }

  @Test
  public void shouldGetFantasySeasons() {
    //given
    final List<Integer> seasons = List.of(2019, 2020);
    when(fantasyDefinitionRepository.getSeasons(series)).thenReturn(seasons);

    //when & then
    assertEquals(seasons, fantasyDefinitionService.getFantasySeasons(series));

    verify(fantasyDefinitionRepository).getSeasons(series);
    verify(fantasyDefinitionRepository).getSeasons(anyString());
  }

  @Test
  public void shouldThrowExceptionOnGetNextFantasySeasonsWhenSeriesIsInvalid() {
    assertThrows(InvalidFantasySeriesException.class, () -> fantasyDefinitionService.getFantasySeasons(invalidSeries));
    verify(fantasyDefinitionRepository, never()).getSeasons(anyString());
  }

  @Test
  public void shouldGetFantasyRaceDefinition() {
    final int nextRace = 3;

    List<OriginalRace> originalRaces = IntStream.range(1, 6)
            .boxed()
            .map(round -> OriginalRace.builder().series(series).season(season).round(round).raceName("Race " + round).build())
            .collect(Collectors.toList());

    when(originalRaceService.getRaces(series, season)).thenReturn(originalRaces);
    when(fantasyDefinitionRepository.getNextRace(series, season)).thenReturn(nextRace);

    final FantasyRaceDefinition expectedFantasyRaceDefinition = FantasyRaceDefinition
            .builder()
            .series(series)
            .season(season)
            .fantasyRaces(toFantasyRaceViewItem(originalRaces.stream().filter(originalRace -> originalRace.getRound() <= nextRace).collect(Collectors.toList())))
            .selectedFantasyRaceId(createFantasyRaceViewItemId(series, season, nextRace))
            .build();

    assertEquals(expectedFantasyRaceDefinition, fantasyDefinitionService.getFantasyRaceDefinition(series, season));

    verify(originalRaceService).getRaces(series, season);
    verify(fantasyDefinitionRepository).getNextRace(series, season);
  }

  @Test
  public void shouldThrowExceptionOnGetFantasyRaceDefinitionWhenSeriesIsInvalid() {
    assertThrows(InvalidFantasySeriesException.class, () -> fantasyDefinitionService.getFantasyRaceDefinition(invalidSeries, season));

    verify(originalRaceService, never()).getRaces(anyString(), anyInt());
    verify(fantasyDefinitionRepository, never()).getNextRace(anyString(), anyInt());
  }

  @Test
  void shouldGetInitialMoney() {
    final long expectedInitialMoney = 1_000_00;
    when(fantasyDefinitionRepository.getInitialMoney(series, season)).thenReturn(expectedInitialMoney);
    assertEquals(expectedInitialMoney, fantasyDefinitionService.getInitialMoney(series, season));
  }

  @Test
  void shouldThrowExceptionOnGetInitialMoneyWhenSeriesIsInvalid() {
    assertThrows(InvalidFantasySeriesException.class, () -> fantasyDefinitionService.getInitialMoney(invalidSeries, season));
    verify(fantasyDefinitionRepository, never()).getInitialMoney(anyString(), anyInt());
  }

  @Test
  void shouldGetInitialDriverPrice() {
    final int expectedDriverPrice = 15_000_00;
    when(fantasyDefinitionRepository.getInitialDriverPrice(series, season)).thenReturn(expectedDriverPrice);
    assertEquals(expectedDriverPrice, fantasyDefinitionService.getInitialDriverPrice(series, season));
  }

  @Test
  void shouldThrowExceptionOnGetInitialDriverPriceWhenSeriesIsInvalid() {
    assertThrows(InvalidFantasySeriesException.class, () -> fantasyDefinitionService.getInitialDriverPrice(invalidSeries, season));
    verify(fantasyDefinitionRepository, never()).getInitialDriverPrice(anyString(), anyInt());
  }

  @Test
  void shouldGetInitialConstructorPrice() {
    final int expectedConstructorPrice = 10_000_00;
    when(fantasyDefinitionRepository.getInitialConstructorPrice(series, season)).thenReturn(expectedConstructorPrice);
    assertEquals(expectedConstructorPrice, fantasyDefinitionService.getInitialConstructorPrice(series, season));
  }

  @Test
  void shouldThrowExceptionOnGetInitialConstructorPriceWhenSeriesIsInvalid() {
    assertThrows(InvalidFantasySeriesException.class, () -> fantasyDefinitionService.getInitialConstructorPrice(invalidSeries, season));
    verify(fantasyDefinitionRepository, never()).getInitialConstructorPrice(anyString(), anyInt());
  }
}
