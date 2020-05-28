package com.aklysoft.fantasyf1.service.original.results;

import com.aklysoft.fantasyf1.service.original.results.model.ERaceResultExt;

import java.util.function.BiFunction;
import java.util.function.Function;

public class OriginalRaceResultMappers {

  public static Function<ERaceResultExt, OriginalRaceResultPK> getOriginalRaceResultIdMapper(String series, int year) {
    return raceResult ->
            OriginalRaceResultPK.
                    builder()
                    .series(series)
                    .season(year)
                    .round(raceResult.getRound())
                    .position(raceResult.getPosition())
                    .build();
  }


  static BiFunction<ERaceResultExt, OriginalRaceResultPK, OriginalRaceResult> originalRaceResultMapper =
          (raceResult, raceResultId) ->
                  OriginalRaceResult
                          .builder()
                          .series(raceResultId.getSeries())
                          .season(raceResultId.getSeason())
                          .round(raceResultId.getRound())
                          .position(raceResultId.getPosition())
                          .grid(raceResult.getGrid())
                          .laps(raceResult.getLaps())
                          .points(raceResult.getPoints())
                          .number(raceResult.getNumber())
                          .positionText(raceResult.getPositionText())
                          .status(raceResult.getStatus())
                          .build();

}
