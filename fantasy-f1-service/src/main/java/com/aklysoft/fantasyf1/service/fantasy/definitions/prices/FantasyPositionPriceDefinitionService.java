package com.aklysoft.fantasyf1.service.fantasy.definitions.prices;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class FantasyPositionPriceDefinitionService {

  private final FantasyPositionPriceDefinitionRepository fantasyPositionPriceDefinitionRepository;

  public FantasyPositionPriceDefinitionService(FantasyPositionPriceDefinitionRepository fantasyPositionPriceDefinitionRepository) {
    this.fantasyPositionPriceDefinitionRepository = fantasyPositionPriceDefinitionRepository;
  }

  public List<FantasyPositionPriceDefinition> getInitialDriverPositionPrices(String series, int season) {
    return fantasyPositionPriceDefinitionRepository.getFantasyPositionPriceDefinitions(series, season, FantasyPositionPriceDefinitionType.INITIAL_DRIVER_PRICE_PERCENT);
  }

  public List<FantasyPositionPriceDefinition> getInitialConstructorPositionPrices(String series, int season) {
    return fantasyPositionPriceDefinitionRepository.getFantasyPositionPriceDefinitions(series, season, FantasyPositionPriceDefinitionType.INITIAL_CONSTRUCTOR_PRICE_PERCENT);
  }

  public List<FantasyPositionPriceDefinition> getRaceDriverPositionPrices(String series, int season) {
    return fantasyPositionPriceDefinitionRepository.getFantasyPositionPriceDefinitions(series, season, FantasyPositionPriceDefinitionType.RACE_DRIVER_PRICE_PERCENT);
  }

  public List<FantasyPositionPriceDefinition> getRaceConstructorPositionPrices(String series, int season) {
    return fantasyPositionPriceDefinitionRepository.getFantasyPositionPriceDefinitions(series, season, FantasyPositionPriceDefinitionType.RACE_CONSTRUCTOR_PRICE_PERCENT);
  }
}
