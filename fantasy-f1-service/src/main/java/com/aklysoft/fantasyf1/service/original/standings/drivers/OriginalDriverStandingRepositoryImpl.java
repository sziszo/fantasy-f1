package com.aklysoft.fantasyf1.service.original.standings.drivers;

import com.aklysoft.fantasyf1.service.original.OriginalRepositoryImpl;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OriginalDriverStandingRepositoryImpl extends OriginalRepositoryImpl<OriginalDriverStanding, OriginalDriverStandingPK> implements OriginalDriverStandingRepository {

  public OriginalDriverStandingRepositoryImpl() {
    super(OriginalDriverStanding.class, OriginalDriverStandingPK.class);
  }


}
