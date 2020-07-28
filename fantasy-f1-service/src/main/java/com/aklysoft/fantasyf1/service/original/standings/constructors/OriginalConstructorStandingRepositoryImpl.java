package com.aklysoft.fantasyf1.service.original.standings.constructors;

import com.aklysoft.fantasyf1.service.original.OriginalRepositoryImpl;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OriginalConstructorStandingRepositoryImpl extends OriginalRepositoryImpl<OriginalConstructorStanding, OriginalConstructorStandingPK> implements OriginalConstructorStandingRepository {

  public OriginalConstructorStandingRepositoryImpl() {
    super(OriginalConstructorStanding.class, OriginalConstructorStandingPK.class);
  }


}
