package com.aklysoft.fantasyf1.service.fantasy.members;

import com.aklysoft.fantasyf1.service.core.exceptions.ApiException;

public class FantasyTeamMemberNotExistException extends ApiException {
  public FantasyTeamMemberNotExistException(String message) {
    super(message);
  }
}
