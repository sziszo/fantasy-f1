package com.aklysoft.fantasyf1.service.original.races;

import com.aklysoft.fantasyf1.service.original.OriginalRepositoryImpl;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OriginalRaceRepositoryImpl extends OriginalRepositoryImpl<OriginalRace, OriginalRacePK> implements OriginalRaceRepository {

  public OriginalRaceRepositoryImpl() {
    super(OriginalRace.class, OriginalRacePK.class);
  }

}
