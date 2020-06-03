package com.aklysoft.fantasyf1.service.fantasy.teams;

import com.aklysoft.fantasyf1.service.core.exceptions.ApiException;

public class FantasyTeamNotExistException extends ApiException {
  public FantasyTeamNotExistException(String teamName) {
    super("Team '" + teamName + "' is missing!");
  }
}
