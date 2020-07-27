package com.aklysoft.fantasyf1.service.fantasy.constructors;

import com.aklysoft.fantasyf1.service.fantasy.definitions.FantasyDefinitionService;
import com.aklysoft.fantasyf1.service.fantasy.definitions.prices.FantasyPositionPriceDefinition;
import com.aklysoft.fantasyf1.service.fantasy.definitions.prices.FantasyPositionPriceDefinitionService;
import com.aklysoft.fantasyf1.service.original.constructors.OriginalConstructor;
import com.aklysoft.fantasyf1.service.original.constructors.OriginalConstructorService;
import com.aklysoft.fantasyf1.service.original.standings.constructors.OriginalConstructorStanding;
import com.aklysoft.fantasyf1.service.original.standings.constructors.OriginalConstructorStandingService;
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
class FantasyConstructorServiceTest {

  @InjectMocks
  FantasyConstructorService fantasyConstructorService;

  @Mock
  OriginalConstructorService originalConstructorService;

  @Mock
  OriginalConstructorStandingService originalConstructorStandingService;

  @Mock
  FantasyPositionPriceDefinitionService fantasyPositionPriceDefinitionService;

  @Mock
  FantasyDefinitionService fantasyDefinitionService;

  private static final String CONSTRUCTOR_ID_PATTERN = "constructor-%d";

  @Test
  void testCalculateInitialPrices() {

    //given
    final String series = "f1";
    final int season = 2019;
    final int prevSeason = 2018;

    final int initialConstructorPrice = 15_000_000;

    final int ConstructorCount = 3;

    when(originalConstructorStandingService.getConstructorStandings(series, prevSeason)).thenReturn(createOriginalConstructorStandings(series, prevSeason, ConstructorCount));
    when(fantasyDefinitionService.getInitialConstructorPrice(series, season)).thenReturn(initialConstructorPrice);

    final List<FantasyPositionPriceDefinition> initialConstructorPositionPrices = List.of(
            FantasyPositionPriceDefinition.builder().percent(100).position(1).series(series).season(season).build(),
            FantasyPositionPriceDefinition.builder().percent(50).position(2).series(series).season(season).build(),
            FantasyPositionPriceDefinition.builder().percent(10).position(3).series(series).season(season).build()
    );
    when(fantasyPositionPriceDefinitionService.getInitialConstructorPositionPrices(series, season)).thenReturn(initialConstructorPositionPrices);

    final List<OriginalConstructor> originalConstructors = createOriginalConstructors(series, season, ConstructorCount);
    when(originalConstructorService.getConstructors(series, season)).thenReturn(originalConstructors);

    final List<OriginalConstructor> expectedConstructors = createOriginalConstructors(series, season, ConstructorCount);
    expectedConstructors.get(0).setPrice(initialConstructorPrice * 2L);
    expectedConstructors.get(1).setPrice(Math.round(initialConstructorPrice * 1.5));
    expectedConstructors.get(2).setPrice(Math.round(initialConstructorPrice * 1.1));

    //when
    fantasyConstructorService.calculateInitialPrices(series, season);

    //then
    assertEquals(expectedConstructors, originalConstructors);

    verify(originalConstructorStandingService).getConstructorStandings(series, prevSeason);
    verify(originalConstructorStandingService).getConstructorStandings(anyString(), anyInt());
    verify(fantasyDefinitionService).getInitialConstructorPrice(series, season);
    verify(fantasyDefinitionService).getInitialConstructorPrice(anyString(), anyInt());
    verify(fantasyPositionPriceDefinitionService).getInitialConstructorPositionPrices(series, season);
    verify(fantasyPositionPriceDefinitionService).getInitialConstructorPositionPrices(anyString(), anyInt());
    verify(originalConstructorService).getConstructors(series, season);
    verify(originalConstructorService).getConstructors(anyString(), anyInt());
  }

  private List<OriginalConstructorStanding> createOriginalConstructorStandings(String series, int season, int count) {
    return IntStream.range(1, count + 1).boxed()
            .map(nr ->
                    OriginalConstructorStanding
                            .builder()
                            .constructorId(String.format(CONSTRUCTOR_ID_PATTERN, nr))
                            .position(nr)
                            .series(series)
                            .season(season)
                            .build()
            )
            .collect(Collectors.toList());
  }

  private List<OriginalConstructor> createOriginalConstructors(String series, int season, int count) {
    return IntStream.range(1, count + 1).boxed()
            .map(nr ->
                    OriginalConstructor
                            .builder()
                            .series(series)
                            .season(season)
                            .id(String.format(CONSTRUCTOR_ID_PATTERN, nr))
                            .build()
            )
            .collect(Collectors.toList());
  }
}
