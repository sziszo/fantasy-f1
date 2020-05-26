package com.aklysoft.fantasyf1.service.original.results;

import com.aklysoft.fantasyf1.service.original.OriginalRepository;

import java.util.List;

public interface OriginalRaceResultRepository extends OriginalRepository<OriginalRaceResult, OriginalRaceResultPK> {
  List<OriginalRaceResult> findAllBySeasonAndRace(String series, int year, int round);
}
