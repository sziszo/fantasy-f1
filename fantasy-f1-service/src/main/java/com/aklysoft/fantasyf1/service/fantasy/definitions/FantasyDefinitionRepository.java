package com.aklysoft.fantasyf1.service.fantasy.definitions;

import com.aklysoft.fantasyf1.service.core.orm.GeneralRepository;

public interface FantasyDefinitionRepository extends GeneralRepository<FantasyDefinition, FantasyDefinitionPK> {
  Integer getCurrentSeason(String series);

  Integer getNextRace(String series, int season);
}
