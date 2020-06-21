package com.aklysoft.fantasyf1.service.fantasy.definitions;

import com.aklysoft.fantasyf1.service.core.exceptions.ApiException;

public class NoNextFantasyRace extends ApiException {
  public NoNextFantasyRace(String series, int season) {
    super("The next race for the " + series + " series in the " + season + " season is unknown!");
  }
}

