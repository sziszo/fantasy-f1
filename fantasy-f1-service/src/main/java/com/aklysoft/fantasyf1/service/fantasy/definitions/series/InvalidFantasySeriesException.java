package com.aklysoft.fantasyf1.service.fantasy.definitions.series;

import com.aklysoft.fantasyf1.service.core.exceptions.ApiException;

public class InvalidFantasySeriesException extends ApiException {
  public InvalidFantasySeriesException(String series) {
    super("Invalid fantasy series! series=" + series);
  }
}
