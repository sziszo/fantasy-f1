package com.aklysoft.fantasyf1.service.fantasy.drivers;

import com.aklysoft.fantasyf1.service.fantasy.definitions.FantasyDefinitionService;
import com.aklysoft.fantasyf1.service.fantasy.definitions.prices.FantasyPositionPriceDefinition;
import com.aklysoft.fantasyf1.service.fantasy.definitions.prices.FantasyPositionPriceDefinitionService;
import com.aklysoft.fantasyf1.service.original.drivers.OriginalDriver;
import com.aklysoft.fantasyf1.service.original.drivers.OriginalDriverService;
import com.aklysoft.fantasyf1.service.original.standings.drivers.OriginalDriverStanding;
import com.aklysoft.fantasyf1.service.original.standings.drivers.OriginalDriverStandingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FantasyDriverServiceTest {

  @InjectMocks
  FantasyDriverService fantasyDriverService;

  @Mock
  OriginalDriverService originalDriverService;

  @Mock
  OriginalDriverStandingService originalDriverStandingService;

  @Mock
  FantasyPositionPriceDefinitionService fantasyPositionPriceDefinitionService;

  @Mock
  FantasyDefinitionService fantasyDefinitionService;

  private static final String DRIVER_ID_PATTERN = "driver-%d";

  @Test
  void testCalculateInitialPrices() {

    //given
    final String series = "f1";
    final int season = 2019;
    final int prevSeason = 2018;

    final int initialDriverPrice = 15_000_000;

    final int driverCount = 3;

    when(originalDriverStandingService.getDriverStandings(series, prevSeason)).thenReturn(createOriginalDriverStandings(series, prevSeason, driverCount));
    when(fantasyDefinitionService.getInitialDriverPrice(series, season)).thenReturn(initialDriverPrice);

    final List<FantasyPositionPriceDefinition> initialDriverPositionPrices = List.of(
            FantasyPositionPriceDefinition.builder().percent(100).position(1).series(series).season(season).build(),
            FantasyPositionPriceDefinition.builder().percent(50).position(2).series(series).season(season).build(),
            FantasyPositionPriceDefinition.builder().percent(10).position(3).series(series).season(season).build()
    );
    when(fantasyPositionPriceDefinitionService.getInitialDriverPositionPrices(series, season)).thenReturn(initialDriverPositionPrices);

    final List<OriginalDriver> originalDrivers = createOriginalDrivers(series, season, driverCount);
    when(originalDriverService.getDrivers(series, season)).thenReturn(originalDrivers);

    final List<OriginalDriver> expectedDrivers = createOriginalDrivers(series, season, driverCount);
    expectedDrivers.get(0).setPrice(initialDriverPrice * 2L);
    expectedDrivers.get(1).setPrice(Math.round(initialDriverPrice * 1.5));
    expectedDrivers.get(2).setPrice(Math.round(initialDriverPrice * 1.1));

    //when
    fantasyDriverService.calculateInitialPrices(series, season);

    //then
    assertEquals(expectedDrivers, originalDrivers);

    verify(originalDriverStandingService).getDriverStandings(series, prevSeason);
    verify(originalDriverStandingService).getDriverStandings(anyString(), anyInt());
    verify(fantasyDefinitionService).getInitialDriverPrice(series, season);
    verify(fantasyDefinitionService).getInitialDriverPrice(anyString(), anyInt());
    verify(fantasyPositionPriceDefinitionService).getInitialDriverPositionPrices(series, season);
    verify(fantasyPositionPriceDefinitionService).getInitialDriverPositionPrices(anyString(), anyInt());
    verify(originalDriverService).getDrivers(series, season);
    verify(originalDriverService).getDrivers(anyString(), anyInt());

  }

  private List<OriginalDriverStanding> createOriginalDriverStandings(String series, int season, int count) {
    return IntStream.range(1, count + 1).boxed()
            .map(nr ->
                    OriginalDriverStanding
                            .builder()
                            .driverId(String.format(DRIVER_ID_PATTERN, nr))
                            .position(nr)
                            .series(series)
                            .season(season)
                            .build()
            )
            .collect(Collectors.toList());
  }

  private List<OriginalDriver> createOriginalDrivers(String series, int season, int count) {
    return IntStream.range(1, count + 1).boxed()
            .map(nr ->
                    OriginalDriver
                            .builder()
                            .series(series)
                            .season(season)
                            .id(String.format(DRIVER_ID_PATTERN, nr))
                            .build()
            )
            .collect(Collectors.toList());
  }


}
