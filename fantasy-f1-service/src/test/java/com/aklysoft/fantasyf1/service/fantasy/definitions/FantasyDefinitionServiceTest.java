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
  public void shouldThrowExceptionWhenGetCurrentSeasonAndSeriesIsInvalid() {
    assertThrows(InvalidFantasySeriesException.class, () -> fantasyDefinitionService.getCurrentSeason("ff"));
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
    assertThrows(InvalidFantasySeriesException.class, () -> fantasyDefinitionService.getNextRace("ff", season));
    verify(fantasyDefinitionRepository, never()).getNextRace(anyString(), anyInt());
  }

  @Test
  public void shouldThrowExceptionWhenGetNextRaceAndSeasonIsInvalid() {
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
  public void shouldThrowExceptionWhenGetNextFantasySeasonsAndSeriesIsInvalid() {
    assertThrows(InvalidFantasySeriesException.class, () -> fantasyDefinitionService.getFantasySeasons("ff"));
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
  public void shouldThrowExceptionWhenGetFantasyRaceDefinitionAndSeriesIsInvalid() {
    assertThrows(InvalidFantasySeriesException.class, () -> fantasyDefinitionService.getFantasyRaceDefinition("ff", season));

    verify(originalRaceService, never()).getRaces(anyString(), anyInt());
    verify(fantasyDefinitionRepository, never()).getNextRace(anyString(), anyInt());
  }
}
