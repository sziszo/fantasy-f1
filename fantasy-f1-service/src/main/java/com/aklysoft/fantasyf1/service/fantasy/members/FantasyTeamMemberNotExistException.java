package com.aklysoft.fantasyf1.service.fantasy.members;

import com.aklysoft.fantasyf1.service.core.exceptions.ApiException;

public class FantasyTeamMemberNotExistException extends ApiException {

  public FantasyTeamMemberNotExistException(FantasyTeamMemberCategoryType teamMemberCategoryType) {
    super("FantasyTeamMember "+ teamMemberCategoryType + " does not exist");
  }
}
