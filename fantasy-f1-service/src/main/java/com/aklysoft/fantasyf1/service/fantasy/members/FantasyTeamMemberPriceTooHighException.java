package com.aklysoft.fantasyf1.service.fantasy.members;

import com.aklysoft.fantasyf1.service.core.exceptions.ApiException;

public class FantasyTeamMemberPriceTooHighException extends ApiException {

  public FantasyTeamMemberPriceTooHighException(FantasyTeamMemberPriceItem constructor) {
    super(constructor.getDisplayName() + " is too expensive!!");
  }
}
