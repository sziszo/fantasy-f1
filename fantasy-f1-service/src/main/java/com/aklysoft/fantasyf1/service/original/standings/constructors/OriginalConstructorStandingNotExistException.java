package com.aklysoft.fantasyf1.service.original.standings.constructors;

import com.aklysoft.fantasyf1.service.core.exceptions.ApiException;

import java.util.Optional;

public class OriginalConstructorStandingNotExistException extends ApiException {
  public OriginalConstructorStandingNotExistException(String series, int season, Integer round) {
    super("ConstructorStanding not exists for " + series + "/" + season + Optional.ofNullable(round).map(r -> "/" + r).orElse(""));
  }
}
