package com.aklysoft.fantasyf1.service.fantasy.definitions;

import com.aklysoft.fantasyf1.service.core.orm.GeneralRepository;

import java.util.List;

public interface FantasyDefinitionRepository extends GeneralRepository<FantasyDefinition, FantasyDefinitionPK> {
  Integer getCurrentSeason(String series);

  Integer getNextRace(String series, int season);

  List<Integer> getSeasons(String series);
}
