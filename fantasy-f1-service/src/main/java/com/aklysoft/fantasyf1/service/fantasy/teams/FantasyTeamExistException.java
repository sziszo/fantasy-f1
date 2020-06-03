package com.aklysoft.fantasyf1.service.fantasy.teams;

import com.aklysoft.fantasyf1.service.core.exceptions.ApiException;

public class FantasyTeamExistException extends ApiException {
  public FantasyTeamExistException(String teamName) {
    super("Team '" + teamName + "' is already exist!");
  }
}
