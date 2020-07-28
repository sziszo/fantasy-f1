package com.aklysoft.fantasyf1.service.original.standings.drivers;

import com.aklysoft.fantasyf1.service.core.exceptions.ApiException;

import java.util.Optional;

public class OriginalDriverStandingNotExistException extends ApiException {
  public OriginalDriverStandingNotExistException(String series, int season, Integer round) {
    super("DriverStanding not exists for " + series + "/" + season + Optional.ofNullable(round).map(r -> "/" + r).orElse(""));
  }
}
