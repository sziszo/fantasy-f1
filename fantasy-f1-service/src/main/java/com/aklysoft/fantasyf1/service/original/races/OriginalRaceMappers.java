package com.aklysoft.fantasyf1.service.original.races;

import com.aklysoft.fantasyf1.service.core.utils.DateUtils;
import com.aklysoft.fantasyf1.service.original.races.model.ERace;

import java.util.function.BiFunction;
import java.util.function.Function;

public class OriginalRaceMappers {

  static BiFunction<ERace, OriginalRacePK, OriginalRace> originalRaceMapper =
          (race, id) ->
                  OriginalRace
                          .builder()
                          .series(id.getSeries())
                          .season(id.getSeason())
                          .round(id.getRound())
                          .raceName(race.getRaceName())
                          .date(DateUtils.toUtc(String.format("%sT%s", race.getDate(), race.getTime())))
                          .build();

  public static Function<ERace, OriginalRacePK> getOriginalRaceIdMapper(String series) {
    return race ->
            OriginalRacePK
                    .builder()
                    .series(series)
                    .season(race.getSeason())
                    .round(race.getRound())
                    .build();
  }

}
