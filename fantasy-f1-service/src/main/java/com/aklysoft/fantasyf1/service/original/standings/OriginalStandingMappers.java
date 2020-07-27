package com.aklysoft.fantasyf1.service.original.standings;

import com.aklysoft.fantasyf1.service.original.standings.constructors.OriginalConstructorStanding;
import com.aklysoft.fantasyf1.service.original.standings.constructors.OriginalConstructorStandingPK;
import com.aklysoft.fantasyf1.service.original.standings.drivers.OriginalDriverStanding;
import com.aklysoft.fantasyf1.service.original.standings.drivers.OriginalDriverStandingPK;
import com.aklysoft.fantasyf1.service.original.standings.model.EConstructorStanding;
import com.aklysoft.fantasyf1.service.original.standings.model.EDriverStanding;

import java.util.function.BiFunction;
import java.util.function.Function;

public class OriginalStandingMappers {

  private OriginalStandingMappers() {
  }

  public static Function<EDriverStanding, OriginalDriverStandingPK> getDriverStandingIdMapper(String series, int year) {
    return data -> OriginalDriverStandingPK
            .builder()
            .series(series)
            .season(year)
            .driverId(data.getDriver().getDriverId())
            .build();
  }

  public static BiFunction<EDriverStanding, OriginalDriverStandingPK, OriginalDriverStanding> driverStandingMapper =
          (data, id) ->
                  OriginalDriverStanding
                          .builder()
                          .series(id.getSeries())
                          .season(id.getSeason())
                          .driverId(id.getDriverId())
                          .position(data.getPosition())
                          .points(data.getPoints())
                          .wins(data.getWins())
                          .build();

  public static Function<EConstructorStanding, OriginalConstructorStandingPK> getConstructorStandingIdMapper(String series, int year) {
    return data -> OriginalConstructorStandingPK
            .builder()
            .series(series)
            .season(year)
            .constructorId(data.getConstructor().getConstructorId())
            .build();
  }

  public static BiFunction<EConstructorStanding, OriginalConstructorStandingPK, OriginalConstructorStanding> constructorStandingMapper =
          (data, id) ->
                  OriginalConstructorStanding
                          .builder()
                          .series(id.getSeries())
                          .season(id.getSeason())
                          .constructorId(id.getConstructorId())
                          .position(data.getPosition())
                          .points(data.getPoints())
                          .wins(data.getWins())
                          .build();
}
