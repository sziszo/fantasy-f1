package com.aklysoft.fantasyf1.service.fantasy.definitions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FantasyDefinitionServiceTest {

  @InjectMocks
  FantasyDefinitionService fantasyDefinitionService;

  @Mock
  FantasyDefinitionRepository fantasyDefinitionRepository;

  final String series = "f1";
  final int season = 2019;
  final int race = 1;

  @Test
  public void shouldGetCurrentSeason() {
    when(fantasyDefinitionRepository.getCurrentSeason(series)).thenReturn(season);
    assertEquals(season, fantasyDefinitionService.getCurrentSeason(series));
  }

  @Test
  public void shouldGetNextRace() {
    when(fantasyDefinitionRepository.getNextRace(series, season)).thenReturn(race);
    assertEquals(race, fantasyDefinitionService.getNextRace(series, season));
  }
}
