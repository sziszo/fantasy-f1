package com.aklysoft.fantasyf1.service.fantasy.constructors;

import com.aklysoft.fantasyf1.service.fantasy.definitions.FantasyDefinitionService;
import com.aklysoft.fantasyf1.service.fantasy.definitions.prices.FantasyPositionPriceDefinition;
import com.aklysoft.fantasyf1.service.fantasy.definitions.prices.FantasyPositionPriceDefinitionService;
import com.aklysoft.fantasyf1.service.original.constructors.OriginalConstructorService;
import com.aklysoft.fantasyf1.service.original.standings.constructors.OriginalConstructorStanding;
import com.aklysoft.fantasyf1.service.original.standings.constructors.OriginalConstructorStandingService;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@ApplicationScoped
public class FantasyConstructorService {

  private final OriginalConstructorService originalConstructorService;
  private final OriginalConstructorStandingService originalConstructorStandingService;
  private final FantasyPositionPriceDefinitionService fantasyPositionPriceDefinitionService;
  private final FantasyDefinitionService fantasyDefinitionService;

  public FantasyConstructorService(OriginalConstructorService originalConstructorService,
                                   OriginalConstructorStandingService originalConstructorStandingService,
                                   FantasyPositionPriceDefinitionService fantasyPositionPriceDefinitionService,
                                   FantasyDefinitionService fantasyDefinitionService) {
    this.originalConstructorService = originalConstructorService;
    this.originalConstructorStandingService = originalConstructorStandingService;
    this.fantasyPositionPriceDefinitionService = fantasyPositionPriceDefinitionService;
    this.fantasyDefinitionService = fantasyDefinitionService;
  }

  @Transactional
  public void calculateInitialPrices(String series, int season) {

    final Map<String, Integer> standings = originalConstructorStandingService.getConstructorStandings(series, season - 1)
            .stream()
            .collect(toMap(OriginalConstructorStanding::getConstructorId, OriginalConstructorStanding::getPosition));

    final Map<Integer, Integer> percents = fantasyPositionPriceDefinitionService.getInitialConstructorPositionPrices(series, season)
            .stream()
            .collect(toMap(FantasyPositionPriceDefinition::getPosition, FantasyPositionPriceDefinition::getPercent));

    final Function<String, Double> getPercent = (ConstructorId) -> (double) (100 + percents.get(standings.getOrDefault(ConstructorId, 0))) / 100;

    final int initialPrice = fantasyDefinitionService.getInitialConstructorPrice(series, season);

    originalConstructorService.getConstructors(series, season)
            .forEach(constructor -> constructor.setPrice(Math.round(initialPrice * getPercent.apply(constructor.getId()))));

  }
}
