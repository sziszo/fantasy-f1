package com.aklysoft.fantasyf1.service.original.standings.constructors;

import com.aklysoft.fantasyf1.service.original.EResponse;
import com.aklysoft.fantasyf1.service.original.ErgastDownloaderService;
import com.aklysoft.fantasyf1.service.original.constructors.model.EConstructor;
import com.aklysoft.fantasyf1.service.original.standings.model.EConstructorStanding;
import com.aklysoft.fantasyf1.service.original.standings.model.EConstructorStandingData;
import com.aklysoft.fantasyf1.service.original.standings.model.EConstructorStandingList;
import com.aklysoft.fantasyf1.service.original.standings.model.EStandingTable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OriginalConstructorStandingServiceTest {

  @InjectMocks
  OriginalConstructorStandingService originalConstructorStandingService;

  @Mock
  ErgastDownloaderService downloaderService;

  @Mock
  OriginalConstructorStandingRepository originalConstructorStandingRepository;

  final String series = "f1";
  final int season = 2019;

  @Test
  void testGetConstructorStandings() {

    //given
    final EConstructorStanding e1 = new EConstructorStanding();
    e1.setConstructor(createEConstructor("mercedes", "Mercedes"));
    e1.setPosition(1);
    e1.setPoints(655);
    e1.setWins(11);

    final EConstructorStanding e2 = new EConstructorStanding();
    e2.setConstructor(createEConstructor("ferrari", "Ferrari"));
    e2.setPosition(2);
    e2.setPoints(571);
    e2.setWins(6);

    final EConstructorStanding e3 = new EConstructorStanding();
    e3.setConstructor(createEConstructor("red_bull", "Red Bull"));
    e3.setPosition(3);
    e3.setPoints(419);
    e3.setWins(4);

    final EConstructorStandingList ConstructorStandingList = new EConstructorStandingList();
    ConstructorStandingList.setSeason(season);
    ConstructorStandingList.setRound(21);
    ConstructorStandingList.setConstructorStandings(List.of(e1, e2, e3));

    final EStandingTable<EConstructorStandingList> standingTable = new EStandingTable<>();
    standingTable.setSeason(season);
    standingTable.setStandingLists(List.of(ConstructorStandingList));

    final EConstructorStandingData ConstructorStandingData = new EConstructorStandingData();
    ConstructorStandingData.setStandingTable(standingTable);

    final EResponse<EConstructorStandingData> resp = new EResponse<>();
    resp.setData(ConstructorStandingData);

    when(downloaderService.getConstructorStandings(series, season)).thenReturn(resp);

    List<OriginalConstructorStanding> expectedConstructorStandings = List.of(
            OriginalConstructorStanding.builder().series(series).season(season).constructorId("mercedes").position(1).points(655).wins(11).build(),
            OriginalConstructorStanding.builder().series(series).season(season).constructorId("ferrari").position(2).points(571).wins(6).build(),
            OriginalConstructorStanding.builder().series(series).season(season).constructorId("red_bull").position(3).points(419).wins(4).build()
    );

    //when
    final List<OriginalConstructorStanding> result = originalConstructorStandingService.getConstructorStandings(series, season);

    //then
    assertNotNull(result);
    assertEquals(expectedConstructorStandings, result);

    verify(downloaderService).getConstructorStandings(series, season);
    verify(downloaderService).getConstructorStandings(anyString(), anyInt());
    verify(originalConstructorStandingRepository, times(3)).persist(any(OriginalConstructorStanding.class));
    verify(originalConstructorStandingRepository).persist(expectedConstructorStandings.get(0));
    verify(originalConstructorStandingRepository).persist(expectedConstructorStandings.get(1));
    verify(originalConstructorStandingRepository).persist(expectedConstructorStandings.get(2));
  }

  private EConstructor createEConstructor(String ConstructorId, String name) {
    EConstructor Constructor = new EConstructor();
    Constructor.setConstructorId(ConstructorId);
    Constructor.setName(name);
    return Constructor;
  }

  @Test
  void testGetConstructorStandingsAfterRound2() {

    //given
    var round = 2;

    final EConstructorStanding e1 = new EConstructorStanding();
    e1.setConstructor(createEConstructor("mercedes", "Mercedes"));
    e1.setPosition(1);
    e1.setPoints(655);
    e1.setWins(11);

    final EConstructorStanding e2 = new EConstructorStanding();
    e2.setConstructor(createEConstructor("ferrari", "Ferrari"));
    e2.setPosition(2);
    e2.setPoints(571);
    e2.setWins(6);

    final EConstructorStanding e3 = new EConstructorStanding();
    e3.setConstructor(createEConstructor("red_bull", "Red Bull"));
    e3.setPosition(3);
    e3.setPoints(419);
    e3.setWins(4);

    final EConstructorStandingList ConstructorStandingList = new EConstructorStandingList();
    ConstructorStandingList.setSeason(season);
    ConstructorStandingList.setRound(21);
    ConstructorStandingList.setConstructorStandings(List.of(e1, e2, e3));

    final EStandingTable<EConstructorStandingList> standingTable = new EStandingTable<>();
    standingTable.setSeason(season);
    standingTable.setStandingLists(List.of(ConstructorStandingList));

    final EConstructorStandingData ConstructorStandingData = new EConstructorStandingData();
    ConstructorStandingData.setStandingTable(standingTable);

    final EResponse<EConstructorStandingData> resp = new EResponse<>();
    resp.setData(ConstructorStandingData);

    when(downloaderService.getConstructorStandings(series, season, round)).thenReturn(resp);

    List<OriginalConstructorStanding> expectedConstructorStandings = List.of(
            OriginalConstructorStanding.builder().series(series).season(season).constructorId("mercedes").position(1).points(655).wins(11).build(),
            OriginalConstructorStanding.builder().series(series).season(season).constructorId("ferrari").position(2).points(571).wins(6).build(),
            OriginalConstructorStanding.builder().series(series).season(season).constructorId("red_bull").position(3).points(419).wins(4).build()
    );

    //when
    final List<OriginalConstructorStanding> result = originalConstructorStandingService.getConstructorStandings(series, season, round);

    //then
    assertNotNull(result);
    assertEquals(expectedConstructorStandings, result);

    verify(downloaderService).getConstructorStandings(series, season, round);
    verify(downloaderService).getConstructorStandings(anyString(), anyInt(), anyInt());
    verify(originalConstructorStandingRepository, times(3)).persist(any(OriginalConstructorStanding.class));
    verify(originalConstructorStandingRepository).persist(expectedConstructorStandings.get(0));
    verify(originalConstructorStandingRepository).persist(expectedConstructorStandings.get(1));
    verify(originalConstructorStandingRepository).persist(expectedConstructorStandings.get(2));
  }

  @Test
  void shouldThrowOriginalConstructorStandingNotExistExceptionWhenMissingDownloadedDataOnGetConstructorStandings() {

    final EStandingTable<EConstructorStandingList> standingTable = new EStandingTable<>();
    standingTable.setSeason(season);
    standingTable.setStandingLists(List.of());

    final EConstructorStandingData ConstructorStandingData = new EConstructorStandingData();
    ConstructorStandingData.setStandingTable(standingTable);

    final EResponse<EConstructorStandingData> resp = new EResponse<>();
    resp.setData(ConstructorStandingData);

    when(downloaderService.getConstructorStandings(series, season)).thenReturn(resp);

    //when
    assertThrows(OriginalConstructorStandingNotExistException.class, () -> originalConstructorStandingService.getConstructorStandings(series, season));

    verify(downloaderService).getConstructorStandings(series, season);
    verify(downloaderService).getConstructorStandings(anyString(), anyInt());
  }

  @Test
  void shouldThrowOriginalConstructorStandingNotExistExceptionWhenMissingDownloadedDataOnGetConstructorStandingsAfterRound2() {

    final var round = 2;

    final EStandingTable<EConstructorStandingList> standingTable = new EStandingTable<>();
    standingTable.setSeason(season);
    standingTable.setStandingLists(List.of());

    final EConstructorStandingData ConstructorStandingData = new EConstructorStandingData();
    ConstructorStandingData.setStandingTable(standingTable);

    final EResponse<EConstructorStandingData> resp = new EResponse<>();
    resp.setData(ConstructorStandingData);

    when(downloaderService.getConstructorStandings(series, season, round)).thenReturn(resp);

    //when
    assertThrows(OriginalConstructorStandingNotExistException.class, () -> originalConstructorStandingService.getConstructorStandings(series, season, round));

    verify(downloaderService).getConstructorStandings(series, season, round);
    verify(downloaderService).getConstructorStandings(anyString(), anyInt(), anyInt());
  }
}
