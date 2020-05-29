package com.aklysoft.fantasyf1.service.fantasy.teams;

import com.aklysoft.fantasyf1.service.fantasy.definitions.FantasyDefinitionService;
import com.aklysoft.fantasyf1.service.players.PlayerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FantasyTeamServiceTest {

  @InjectMocks
  FantasyTeamService fantasyTeamService;

  @Mock
  private FantasyTeamRepository fantasyTeamRepository;

  @Mock
  private PlayerService playerService;

  @Mock
  private FantasyDefinitionService fantasyDefinitionService;

  private final String series = "f1";
  private final int season = 2019;
  private final int race = 1;
  private final String username = "user";


  @Test
  public void shouldCreateFantasyTeam() {

    //given
    final String teamName = "MyF1Team";

    when(fantasyDefinitionService.getCurrentSeason(series)).thenReturn(season);
    when(fantasyDefinitionService.getNextRace(series, season)).thenReturn(race);

    when(fantasyTeamRepository.isExistName(teamName, username)).thenReturn(false);

    final FantasyTeamPK fantasyTeamId = FantasyTeamPK.builder().series(series).season(season).userName(username).build();
    when(fantasyTeamRepository.findById(fantasyTeamId)).thenReturn(null);

    final NewFantasyTeam newFantasyTeam = NewFantasyTeam
            .builder()
            .series(series)
            .season(season)
            .name(teamName)
            .username(username)
            .creator(username)
            .build();

    //when
    final FantasyTeam result = fantasyTeamService.createFantasyTeam(newFantasyTeam);

    //then
    assertNotNull(result);
    assertEquals(teamName, result.getName());
    assertEquals(username, result.getUserName());
    assertEquals(username, result.getCreator());
    assertNotNull(result.getCreated());


    verify(fantasyDefinitionService).getCurrentSeason(series);
    verify(fantasyDefinitionService).getCurrentSeason(anyString());

    verify(fantasyTeamRepository).isExistName(teamName, username);
    verify(fantasyTeamRepository).isExistName(anyString(), anyString());

    verify(fantasyTeamRepository).findById(fantasyTeamId);
    verify(fantasyTeamRepository).findById(any(FantasyTeamPK.class));

    verify(fantasyDefinitionService).getNextRace(series, season);
    verify(fantasyDefinitionService).getNextRace(anyString(), anyInt());

  }

  @Test
  public void shouldThrowExceptionOnCreationWhenFantasyTeamNameExist() {
    //given
    final String teamName = "MyF1Team";

    when(fantasyDefinitionService.getCurrentSeason(series)).thenReturn(season);
    when(fantasyTeamRepository.isExistName(teamName, username)).thenReturn(true);

    final NewFantasyTeam newFantasyTeam = NewFantasyTeam
            .builder()
            .series(series)
            .season(season)
            .name(teamName)
            .username(username)
            .creator(username)
            .build();

    assertThrows(FantasyTeamNameExistException.class, () -> fantasyTeamService.createFantasyTeam(newFantasyTeam));

    verify(fantasyDefinitionService).getCurrentSeason(series);
    verify(fantasyDefinitionService).getCurrentSeason(anyString());

    verify(fantasyTeamRepository).isExistName(teamName, username);
    verify(fantasyTeamRepository).isExistName(anyString(), anyString());

    verify(fantasyDefinitionService, never()).getNextRace(anyString(), anyInt());
    verify(fantasyTeamRepository, never()).findById(any(FantasyTeamPK.class));

  }

  @Test
  public void shouldThrowExceptionOnCreationWhenFantasyTeamExist() {
    //given
    final String teamName = "MyF1Team";

    when(fantasyDefinitionService.getCurrentSeason(series)).thenReturn(season);
    when(fantasyTeamRepository.isExistName(teamName, username)).thenReturn(false);

    final FantasyTeamPK fantasyTeamId = FantasyTeamPK.builder().series(series).season(season).userName(username).build();
    when(fantasyTeamRepository.findById(fantasyTeamId)).thenReturn(FantasyTeam.builder().build());

    final NewFantasyTeam newFantasyTeam = NewFantasyTeam
            .builder()
            .series(series)
            .season(season)
            .name(teamName)
            .username(username)
            .creator(username)
            .build();

    assertThrows(FantasyTeamExistException.class, () -> fantasyTeamService.createFantasyTeam(newFantasyTeam));

    verify(fantasyDefinitionService).getCurrentSeason(series);
    verify(fantasyDefinitionService).getCurrentSeason(anyString());

    verify(fantasyTeamRepository).isExistName(teamName, username);
    verify(fantasyTeamRepository).isExistName(anyString(), anyString());

    verify(fantasyTeamRepository).findById(fantasyTeamId);
    verify(fantasyTeamRepository).findById(any(FantasyTeamPK.class));

    verify(fantasyDefinitionService, never()).getNextRace(anyString(), anyInt());
  }

  @Test
  public void shouldUpdateFantasyTeam() {

    //given
    final String oldTeamName = "OldF1Team";
    final String newTeamName = "NewF1Team";

    when(fantasyDefinitionService.getCurrentSeason(series)).thenReturn(season);

    when(fantasyTeamRepository.isExistName(newTeamName, username)).thenReturn(false);

    final FantasyTeamPK fantasyTeamId = FantasyTeamPK.builder().series(series).season(season).userName(username).build();
    when(fantasyTeamRepository.findById(fantasyTeamId)).thenReturn(
            FantasyTeam.builder().series(series).season(season).userName(username).name(oldTeamName).build());

    final ModifyFantasyTeam modifyFantasyTeam =
            ModifyFantasyTeam
                    .builder()
                    .series(series)
                    .season(season)
                    .username(username)
                    .modifier(username)
                    .name(newTeamName)
                    .build();

    //when
    final FantasyTeam result = fantasyTeamService.updateFantasyTeam(modifyFantasyTeam);

    //then
    assertNotNull(result);
    assertEquals(newTeamName, result.getName());
    assertEquals(username, result.getUserName());
    assertEquals(username, result.getModifier());
    assertNotNull(result.getModified());


    verify(fantasyDefinitionService).getCurrentSeason(series);
    verify(fantasyDefinitionService).getCurrentSeason(anyString());

    verify(fantasyTeamRepository).isExistName(newTeamName, username);
    verify(fantasyTeamRepository).isExistName(anyString(), anyString());

    verify(fantasyTeamRepository).findById(fantasyTeamId);
    verify(fantasyTeamRepository).findById(any(FantasyTeamPK.class));

  }

  @Test
  public void shouldThrowExceptionOnUpdateWhenFantasyTeamNameExist() {
    //given
    final String newTeamName = "NewF1Team";

    when(fantasyTeamRepository.isExistName(newTeamName, username)).thenReturn(true);

    final ModifyFantasyTeam modifyFantasyTeam =
            ModifyFantasyTeam
                    .builder()
                    .series(series)
                    .season(season)
                    .username(username)
                    .modifier(username)
                    .name(newTeamName)
                    .build();

    assertThrows(FantasyTeamNameExistException.class, () -> fantasyTeamService.updateFantasyTeam(modifyFantasyTeam));

    verify(fantasyTeamRepository).isExistName(newTeamName, username);
    verify(fantasyTeamRepository).isExistName(anyString(), anyString());

    verify(fantasyDefinitionService, never()).getCurrentSeason(anyString());
    verify(fantasyTeamRepository, never()).findById(any(FantasyTeamPK.class));

  }

  @Test
  public void shouldThrowExceptionOnUpdateWhenOriginalFantasyTeamNotExist() {
    //given
    final String newTeamName = "NewF1Team";

    when(fantasyTeamRepository.isExistName(newTeamName, username)).thenReturn(false);
    when(fantasyDefinitionService.getCurrentSeason(series)).thenReturn(season);

    final FantasyTeamPK fantasyTeamId = FantasyTeamPK.builder().series(series).season(season).userName(username).build();
    when(fantasyTeamRepository.findById(fantasyTeamId)).thenReturn(null);

    final ModifyFantasyTeam modifyFantasyTeam =
            ModifyFantasyTeam
                    .builder()
                    .series(series)
                    .season(season)
                    .username(username)
                    .modifier(username)
                    .name(newTeamName)
                    .build();

    assertThrows(FantasyTeamNotExistException.class, () -> fantasyTeamService.updateFantasyTeam(modifyFantasyTeam));

    verify(fantasyTeamRepository).isExistName(newTeamName, username);
    verify(fantasyTeamRepository).isExistName(anyString(), anyString());

    verify(fantasyDefinitionService).getCurrentSeason(anyString());
    verify(fantasyDefinitionService).getCurrentSeason(series);

    verify(fantasyTeamRepository).findById(fantasyTeamId);
    verify(fantasyTeamRepository).findById(any(FantasyTeamPK.class));

  }

  @Test
  public void testGetAllFantasyTeams() {

    final List<FantasyTeam> fantasyTeams =
            Map.of("user", "UserF1Team", "john", "JohnF1Team")
                    .entrySet()
                    .stream()
                    .map(entry -> FantasyTeam.builder().series(series).season(season).userName(entry.getKey()).name(entry.getValue()).build())
                    .collect(toList());

    when(fantasyTeamRepository.findAllBySeriesAndSeason(series, season)).thenReturn(fantasyTeams);

    final List<FantasyTeam> result = fantasyTeamService.getAllFantasyTeams(series, season);

    assertNotNull(result);
    assertEquals(fantasyTeams.size(), result.size());
    assertEquals(fantasyTeams, result);

    verify(fantasyTeamRepository).findAllBySeriesAndSeason(series, season);
    verify(fantasyTeamRepository).findAllBySeriesAndSeason(anyString(), anyInt());
  }
}
