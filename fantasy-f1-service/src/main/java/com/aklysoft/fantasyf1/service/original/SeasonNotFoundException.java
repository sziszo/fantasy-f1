package com.aklysoft.fantasyf1.service.original;

import com.aklysoft.fantasyf1.service.core.exceptions.ApiException;

public class SeasonNotFoundException extends ApiException {
  public SeasonNotFoundException(int year) {
    super("Season " + year + " not found");
  }
}
