package com.aklysoft.fantasyf1.service.fantasy.definitions;

import com.aklysoft.fantasyf1.service.core.exceptions.ApiException;
import com.aklysoft.fantasyf1.service.fantasy.definitions.series.FantasySeriesType;
import com.aklysoft.fantasyf1.service.fantasy.definitions.series.InvalidFantasySeriesException;
import com.aklysoft.fantasyf1.service.original.races.OriginalRaceService;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

import static com.aklysoft.fantasyf1.service.fantasy.definitions.FantasyDefinitionMappers.createFantasyRaceViewItemId;
import static java.util.stream.Collectors.toList;

@ApplicationScoped
public class FantasyDefinitionService {

  private final FantasyDefinitionRepository fantasyDefinitionRepository;
  private final OriginalRaceService originalRaceService;

  public FantasyDefinitionService(FantasyDefinitionRepository fantasyDefinitionRepository,
                                  OriginalRaceService originalRaceService) {
    this.fantasyDefinitionRepository = fantasyDefinitionRepository;
    this.originalRaceService = originalRaceService;
  }

  public Integer getCurrentSeason(String series) {
    validateFantasySeries(series);

    return fantasyDefinitionRepository.getCurrentSeason(series);
  }

  public Integer getNextRace(String series, int season) {
    validateFantasySeries(series);

    return Optional.ofNullable(fantasyDefinitionRepository.getNextRace(series, season))
            .orElseThrow(() -> new NoNextFantasyRace(series, season));
  }

  public List<Integer> getFantasySeasons(String series) {
    validateFantasySeries(series);

    return this.fantasyDefinitionRepository.getSeasons(series);
  }

  public FantasyRaceDefinition getFantasyRaceDefinition(String series, int season) {
    validateFantasySeries(series);

    final Integer nextRace = getNextRace(series, season);

    final List<FantasyRaceViewItem> fantasyRaces =
            originalRaceService.getRaces(series, season)
                    .stream()
                    .filter(originalRace -> originalRace.getRound() <= nextRace)
                    .map(FantasyDefinitionMappers::toFantasyRaceViewItem)
                    .collect(toList());

    return FantasyRaceDefinition
            .builder()
            .series(series)
            .season(season)
            .fantasyRaces(fantasyRaces)
            .selectedFantasyRaceId(createFantasyRaceViewItemId(series, season, nextRace))
            .build();
  }

  private boolean validateFantasySeries(String series) {
    Optional.ofNullable(FantasySeriesType.getFantasySeriesType(series))
            .orElseThrow(() -> new InvalidFantasySeriesException(series));
    return true;
  }


}
