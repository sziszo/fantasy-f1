package com.aklysoft.fantasyf1.service.fantasy.teams;

import com.aklysoft.fantasyf1.service.core.exceptions.ApiException;

public class FantasyTeamNameExistException extends ApiException {
  public FantasyTeamNameExistException(String teamName) {
    super("Team with name '" + teamName + "' is already exist!");
  }
}
