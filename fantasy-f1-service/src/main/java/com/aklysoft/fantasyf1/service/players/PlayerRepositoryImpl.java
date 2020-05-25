package com.aklysoft.fantasyf1.service.players;

import com.aklysoft.fantasyf1.service.core.orm.GeneralRepositoryImpl;

import javax.inject.Singleton;

@Singleton
public class PlayerRepositoryImpl extends GeneralRepositoryImpl<Player, String> implements PlayerRepository {
  public PlayerRepositoryImpl() {
    super(Player.class, String.class);
  }
}
