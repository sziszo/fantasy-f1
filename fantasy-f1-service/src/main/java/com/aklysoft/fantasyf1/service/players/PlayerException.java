package com.aklysoft.fantasyf1.service.players;

import com.aklysoft.fantasyf1.service.core.exceptions.ApiException;

public class PlayerException extends ApiException {
  public PlayerException(String message) {
    super(message);
  }
}
