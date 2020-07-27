package com.aklysoft.fantasyf1.service.fantasy.definitions.prices;

import com.aklysoft.fantasyf1.service.core.orm.GeneralRepository;

import java.util.List;

public interface FantasyPositionPriceDefinitionRepository extends GeneralRepository<FantasyPositionPriceDefinition, FantasyPositionPriceDefinitionPK> {
  List<FantasyPositionPriceDefinition> getFantasyPositionPriceDefinitions(String series, int season, FantasyPositionPriceDefinitionType type);
}
