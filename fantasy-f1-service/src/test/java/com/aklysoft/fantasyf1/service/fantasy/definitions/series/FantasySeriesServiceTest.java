package com.aklysoft.fantasyf1.service.fantasy.definitions.series;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FantasySeriesServiceTest {

  @InjectMocks
  FantasySeriesService fantasySeriesService;

  @Mock
  FantasySeriesRepository fantasySeriesRepository;

  @Test
  public void shouldGetFantasySeries() {
    //given
    final List<FantasySeries> fantasySeries = List.of(
            FantasySeries.builder().id("f1").name("Formula 1").build(),
            FantasySeries.builder().id("f2").name("Formula 2").build(),
            FantasySeries.builder().id("f3").name("Formula 3").build());

    when(fantasySeriesRepository.findAll()).thenReturn(fantasySeries);

    //when
    final List<FantasySeries> result = fantasySeriesService.getFantasySeries();

    //then
    assertNotNull(result);
    assertEquals(fantasySeries, result);

  }

}
