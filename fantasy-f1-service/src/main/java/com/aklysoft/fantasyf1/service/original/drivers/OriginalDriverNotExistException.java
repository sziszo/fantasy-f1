package com.aklysoft.fantasyf1.service.original.drivers;

import com.aklysoft.fantasyf1.service.core.exceptions.ApiException;

public class OriginalDriverNotExistException extends ApiException {
  public OriginalDriverNotExistException(String id) {
    super("Driver " + id + " does not exist!");
  }
}
