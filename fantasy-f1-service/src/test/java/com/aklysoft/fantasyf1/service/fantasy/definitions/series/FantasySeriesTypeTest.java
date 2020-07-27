package com.aklysoft.fantasyf1.service.fantasy.definitions.series;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class FantasySeriesTypeTest {


  @Test
  void shouldNotGetFantasySeriesTypeWhenIdIsNull() {
    assertNull(FantasySeriesType.getFantasySeriesType(null));
  }
}
