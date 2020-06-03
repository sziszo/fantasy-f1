package com.aklysoft.fantasyf1.service.fantasy.members;

import com.aklysoft.fantasyf1.service.core.exceptions.ApiException;
import com.aklysoft.fantasyf1.service.fantasy.teams.FantasyTeamPK;
import com.aklysoft.fantasyf1.service.original.constructors.OriginalConstructor;
import com.aklysoft.fantasyf1.service.original.drivers.OriginalDriver;

public class InvalidFantasyTeamMemberException extends ApiException {
  public InvalidFantasyTeamMemberException(OriginalConstructor constructor, FantasyTeamPK teamId) {
    super("Cannot add constructor " + constructor.getId() + " to the team " + teamId + "! One constructor or its driver can be in the team only once! ");
  }

  public InvalidFantasyTeamMemberException(OriginalDriver driver, FantasyTeamPK teamId) {
    super("Cannot add driver " + driver.getId() + " to the team " + teamId + "! One driver or its constructor can be in the team only once! ");
  }
}
