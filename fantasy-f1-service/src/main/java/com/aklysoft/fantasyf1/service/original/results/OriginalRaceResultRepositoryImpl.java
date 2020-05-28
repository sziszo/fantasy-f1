package com.aklysoft.fantasyf1.service.original.results;

import com.aklysoft.fantasyf1.service.original.OriginalRepositoryImpl;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class OriginalRaceResultRepositoryImpl extends OriginalRepositoryImpl<OriginalRaceResult, OriginalRaceResultPK> implements OriginalRaceResultRepository {

  public OriginalRaceResultRepositoryImpl() {
    super(OriginalRaceResult.class, OriginalRaceResultPK.class);
  }

  @Override
  public List<OriginalRaceResult> findAllBySeasonAndRace(String series, int year, int round) {
    return this.entityManager
            .createQuery(" select r from OriginalRaceResult r where r.series = ?1 and r.season = ?2  and r.round = ?3", OriginalRaceResult.class)
            .setParameter(1, series)
            .setParameter(2, year)
            .setParameter(3, round)
            .getResultList();
  }
}
