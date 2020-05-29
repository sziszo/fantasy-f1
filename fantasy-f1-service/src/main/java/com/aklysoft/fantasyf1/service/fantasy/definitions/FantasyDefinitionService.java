package com.aklysoft.fantasyf1.service.fantasy.definitions;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FantasyDefinitionService {

  private final FantasyDefinitionRepository fantasyDefinitionRepository;

  public FantasyDefinitionService(FantasyDefinitionRepository fantasyDefinitionRepository) {
    this.fantasyDefinitionRepository = fantasyDefinitionRepository;
  }

  public Integer getCurrentSeason(String series) {
    return fantasyDefinitionRepository.getCurrentSeason(series);
  }

  public Integer getNextRace(String series, int season) {
    return fantasyDefinitionRepository.getNextRace(series, season);
  }
}
