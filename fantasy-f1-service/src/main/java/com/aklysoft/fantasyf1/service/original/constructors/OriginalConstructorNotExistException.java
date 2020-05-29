package com.aklysoft.fantasyf1.service.original.constructors;

import com.aklysoft.fantasyf1.service.core.exceptions.ApiException;

public class OriginalConstructorNotExistException extends ApiException {
  public OriginalConstructorNotExistException(String id) {
    super("Constructor " + id + " does not exist!");
  }
}
