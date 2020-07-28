package com.aklysoft.fantasyf1.service.original.standings.drivers;

import com.aklysoft.fantasyf1.service.original.EResponse;
import com.aklysoft.fantasyf1.service.original.ErgastDownloaderService;
import com.aklysoft.fantasyf1.service.original.drivers.model.EDriver;
import com.aklysoft.fantasyf1.service.original.standings.model.EDriverStanding;
import com.aklysoft.fantasyf1.service.original.standings.model.EDriverStandingData;
import com.aklysoft.fantasyf1.service.original.standings.model.EDriverStandingList;
import com.aklysoft.fantasyf1.service.original.standings.model.EStandingTable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OriginalDriverStandingServiceTest {

  @InjectMocks
  OriginalDriverStandingService originalDriverStandingService;

  @Mock
  ErgastDownloaderService downloaderService;

  @Mock
  OriginalDriverStandingRepository originalDriverStandingRepository;

  final String series = "f1";
  final int season = 2019;

  @Test
  void testGetDriverStandings() {

    //given
    final EDriverStanding e1 = new EDriverStanding();
    e1.setDriver(createEDriver("hamilton", "Hamilton", "Lewis"));
    e1.setPosition(1);
    e1.setPoints(408);
    e1.setWins(11);

    final EDriverStanding e2 = new EDriverStanding();
    e2.setDriver(createEDriver("vettel", "Vettel", "Sebastian"));
    e2.setPosition(2);
    e2.setPoints(320);
    e2.setWins(5);

    final EDriverStanding e3 = new EDriverStanding();
    e3.setDriver(createEDriver("max_verstappen", "Verstappen", "Max"));
    e3.setPosition(3);
    e3.setPoints(249);
    e3.setWins(2);

    final EDriverStandingList driverStandingList = new EDriverStandingList();
    driverStandingList.setSeason(season);
    driverStandingList.setRound(21);
    driverStandingList.setDriverStandings(List.of(e1, e2, e3));

    final EStandingTable<EDriverStandingList> standingTable = new EStandingTable<>();
    standingTable.setSeason(season);
    standingTable.setStandingLists(List.of(driverStandingList));

    final EDriverStandingData driverStandingData = new EDriverStandingData();
    driverStandingData.setStandingTable(standingTable);

    final EResponse<EDriverStandingData> resp = new EResponse<>();
    resp.setData(driverStandingData);

    when(downloaderService.getDriverStandings(series, season)).thenReturn(resp);

    List<OriginalDriverStanding> expectedDriverStandings = List.of(
            OriginalDriverStanding.builder().series(series).season(season).driverId("hamilton").position(1).points(408).wins(11).build(),
            OriginalDriverStanding.builder().series(series).season(season).driverId("vettel").position(2).points(320).wins(5).build(),
            OriginalDriverStanding.builder().series(series).season(season).driverId("max_verstappen").position(3).points(249).wins(2).build()
    );

    //when
    final List<OriginalDriverStanding> result = originalDriverStandingService.getDriverStandings(series, season);

    //then
    assertNotNull(result);
    assertEquals(expectedDriverStandings, result);

    verify(downloaderService).getDriverStandings(series, season);
    verify(downloaderService).getDriverStandings(anyString(), anyInt());
    verify(originalDriverStandingRepository, times(3)).persist(any(OriginalDriverStanding.class));
    verify(originalDriverStandingRepository).persist(expectedDriverStandings.get(0));
    verify(originalDriverStandingRepository).persist(expectedDriverStandings.get(1));
    verify(originalDriverStandingRepository).persist(expectedDriverStandings.get(2));
  }

  private EDriver createEDriver(String driverId, String familyName, String givenName) {
    EDriver driver = new EDriver();
    driver.setDriverId(driverId);
    driver.setFamilyName(familyName);
    driver.setGivenName(givenName);
    return driver;
  }

  @Test
  void testGetDriverStandingsAfterRound2() {

    //given
    var round = 2;

    final EDriverStanding e1 = new EDriverStanding();
    e1.setDriver(createEDriver("hamilton", "Hamilton", "Lewis"));
    e1.setPosition(1);
    e1.setPoints(408);
    e1.setWins(11);

    final EDriverStanding e2 = new EDriverStanding();
    e2.setDriver(createEDriver("vettel", "Vettel", "Sebastian"));
    e2.setPosition(2);
    e2.setPoints(320);
    e2.setWins(5);

    final EDriverStanding e3 = new EDriverStanding();
    e3.setDriver(createEDriver("max_verstappen", "Verstappen", "Max"));
    e3.setPosition(3);
    e3.setPoints(249);
    e3.setWins(2);

    final EDriverStandingList driverStandingList = new EDriverStandingList();
    driverStandingList.setSeason(season);
    driverStandingList.setRound(21);
    driverStandingList.setDriverStandings(List.of(e1, e2, e3));

    final EStandingTable<EDriverStandingList> standingTable = new EStandingTable<>();
    standingTable.setSeason(season);
    standingTable.setStandingLists(List.of(driverStandingList));

    final EDriverStandingData driverStandingData = new EDriverStandingData();
    driverStandingData.setStandingTable(standingTable);

    final EResponse<EDriverStandingData> resp = new EResponse<>();
    resp.setData(driverStandingData);

    when(downloaderService.getDriverStandings(series, season, round)).thenReturn(resp);

    List<OriginalDriverStanding> expectedDriverStandings = List.of(
            OriginalDriverStanding.builder().series(series).season(season).driverId("hamilton").position(1).points(408).wins(11).build(),
            OriginalDriverStanding.builder().series(series).season(season).driverId("vettel").position(2).points(320).wins(5).build(),
            OriginalDriverStanding.builder().series(series).season(season).driverId("max_verstappen").position(3).points(249).wins(2).build()
    );

    //when
    final List<OriginalDriverStanding> result = originalDriverStandingService.getDriverStandings(series, season, round);

    //then
    assertNotNull(result);
    assertEquals(expectedDriverStandings, result);

    verify(downloaderService).getDriverStandings(series, season, round);
    verify(downloaderService).getDriverStandings(anyString(), anyInt(), anyInt());
    verify(originalDriverStandingRepository, times(3)).persist(any(OriginalDriverStanding.class));
    verify(originalDriverStandingRepository).persist(expectedDriverStandings.get(0));
    verify(originalDriverStandingRepository).persist(expectedDriverStandings.get(1));
    verify(originalDriverStandingRepository).persist(expectedDriverStandings.get(2));
  }

  @Test
  void shouldThrowOriginalDriverStandingNotExistExceptionWhenMissingDownloadedDataOnGetDriverStandings() {

    final EStandingTable<EDriverStandingList> standingTable = new EStandingTable<>();
    standingTable.setSeason(season);
    standingTable.setStandingLists(List.of());

    final EDriverStandingData DriverStandingData = new EDriverStandingData();
    DriverStandingData.setStandingTable(standingTable);

    final EResponse<EDriverStandingData> resp = new EResponse<>();
    resp.setData(DriverStandingData);

    when(downloaderService.getDriverStandings(series, season)).thenReturn(resp);

    //when
    assertThrows(OriginalDriverStandingNotExistException.class, () -> originalDriverStandingService.getDriverStandings(series, season));

    verify(downloaderService).getDriverStandings(series, season);
    verify(downloaderService).getDriverStandings(anyString(), anyInt());
  }

  @Test
  void shouldThrowOriginalDriverStandingNotExistExceptionWhenMissingDownloadedDataOnGetDriverStandingsAfterRound2() {

    final var round = 2;

    final EStandingTable<EDriverStandingList> standingTable = new EStandingTable<>();
    standingTable.setSeason(season);
    standingTable.setStandingLists(List.of());

    final EDriverStandingData DriverStandingData = new EDriverStandingData();
    DriverStandingData.setStandingTable(standingTable);

    final EResponse<EDriverStandingData> resp = new EResponse<>();
    resp.setData(DriverStandingData);

    when(downloaderService.getDriverStandings(series, season, round)).thenReturn(resp);

    //when
    assertThrows(OriginalDriverStandingNotExistException.class, () -> originalDriverStandingService.getDriverStandings(series, season, round));

    verify(downloaderService).getDriverStandings(series, season, round);
    verify(downloaderService).getDriverStandings(anyString(), anyInt(), anyInt());
  }
}
