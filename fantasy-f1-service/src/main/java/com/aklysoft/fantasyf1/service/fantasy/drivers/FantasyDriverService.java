package com.aklysoft.fantasyf1.service.fantasy.drivers;

import com.aklysoft.fantasyf1.service.fantasy.definitions.FantasyDefinitionService;
import com.aklysoft.fantasyf1.service.fantasy.definitions.prices.FantasyPositionPriceDefinition;
import com.aklysoft.fantasyf1.service.fantasy.definitions.prices.FantasyPositionPriceDefinitionService;
import com.aklysoft.fantasyf1.service.original.drivers.OriginalDriverService;
import com.aklysoft.fantasyf1.service.original.standings.drivers.OriginalDriverStanding;
import com.aklysoft.fantasyf1.service.original.standings.drivers.OriginalDriverStandingService;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@ApplicationScoped
public class FantasyDriverService {

  private final OriginalDriverService originalDriverService;
  private final OriginalDriverStandingService originalDriverStandingService;
  private final FantasyPositionPriceDefinitionService fantasyPositionPriceDefinitionService;
  private final FantasyDefinitionService fantasyDefinitionService;

  public FantasyDriverService(OriginalDriverService originalDriverService,
                              OriginalDriverStandingService originalDriverStandingService,
                              FantasyPositionPriceDefinitionService fantasyPositionPriceDefinitionService,
                              FantasyDefinitionService fantasyDefinitionService) {
    this.originalDriverService = originalDriverService;
    this.originalDriverStandingService = originalDriverStandingService;
    this.fantasyPositionPriceDefinitionService = fantasyPositionPriceDefinitionService;
    this.fantasyDefinitionService = fantasyDefinitionService;
  }

  @Transactional
  public void calculateInitialPrices(String series, int season) {

    final Map<String, Integer> standings = originalDriverStandingService.getDriverStandings(series, season - 1)
            .stream()
            .collect(toMap(OriginalDriverStanding::getDriverId, OriginalDriverStanding::getPosition));

    final Map<Integer, Integer> percents = fantasyPositionPriceDefinitionService.getInitialDriverPositionPrices(series, season)
            .stream()
            .collect(toMap(FantasyPositionPriceDefinition::getPosition, FantasyPositionPriceDefinition::getPercent));

    final Function<String, Double> getPercent = (driverId) -> (double) (100 + percents.get(standings.getOrDefault(driverId, 0))) / 100;

    final int initialPrice = fantasyDefinitionService.getInitialDriverPrice(series, season);

    originalDriverService.getDrivers(series, season)
            .forEach(driver -> driver.setPrice(Math.round(initialPrice * getPercent.apply(driver.getId()))));

  }

}
