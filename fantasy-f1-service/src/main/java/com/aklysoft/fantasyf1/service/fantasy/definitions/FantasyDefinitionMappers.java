package com.aklysoft.fantasyf1.service.fantasy.definitions;

import com.aklysoft.fantasyf1.service.original.races.OriginalRace;

import java.util.List;
import java.util.stream.Stream;

import static com.aklysoft.fantasyf1.service.core.utils.StringUtils.SEPARATOR;
import static java.util.stream.Collectors.toList;

public class FantasyDefinitionMappers {

  private FantasyDefinitionMappers() {
  }

  public static List<FantasySeasonViewItem> toFantasySeasonViewItem(String series, List<Integer> seasons) {
    return toFantasySeasonViewItem(series, seasons.stream())
            .collect(toList());
  }

  public static Stream<FantasySeasonViewItem> toFantasySeasonViewItem(String series, Stream<Integer> seasons) {
    return seasons
            .map(season ->
                    FantasySeasonViewItem
                            .builder()
                            .id(createFantasySeasonViewItemId(series, season))
                            .series(series)
                            .season(season)
                            .build());
  }

  public static String createFantasySeasonViewItemId(String series, Integer season) {
    return "fs" + SEPARATOR + series + SEPARATOR + season;
  }

  public static List<FantasyRaceViewItem> toFantasyRaceViewItem(List<OriginalRace> originalRaces) {
    return toFantasyRaceViewItem(originalRaces.stream()).collect(toList());
  }

  public static Stream<FantasyRaceViewItem> toFantasyRaceViewItem(Stream<OriginalRace> originalRaces) {
    return originalRaces.map(FantasyDefinitionMappers::toFantasyRaceViewItem);
  }

  public static FantasyRaceViewItem toFantasyRaceViewItem(OriginalRace originalRace) {
    return FantasyRaceViewItem
            .builder()
            .id(createFantasyRaceViewItemId(originalRace))
            .series(originalRace.getSeries())
            .season(originalRace.getSeason())
            .round(originalRace.getRound())
            .name(originalRace.getRaceName())
            .date(originalRace.getDate())
            .build();
  }

  public static String createFantasyRaceViewItemId(OriginalRace originalRace) {
    return createFantasyRaceViewItemId(originalRace.getSeries(), originalRace.getSeason(), originalRace.getRound());
  }

  public static String createFantasyRaceViewItemId(String series, int season, int round) {
    return "fr" + SEPARATOR + series + SEPARATOR + season + SEPARATOR + round;
  }
}
