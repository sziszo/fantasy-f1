package com.aklysoft.fantasyf1.service.original.drivers;

import com.aklysoft.fantasyf1.service.original.drivers.model.EDriver;

import java.time.LocalDate;
import java.util.function.BiFunction;
import java.util.function.Function;

public class OriginalDriverMappers {

  public static Function<EDriver, OriginalDriverPK> getDriverIdMapper(String series, int year) {
    return data -> OriginalDriverPK
            .builder()
            .series(series)
            .season(year)
            .id(data.getDriverId())
            .build();
  }

  public static BiFunction<EDriver, OriginalDriverPK, OriginalDriver> driverMapper =
          (data, id) ->
                  OriginalDriver
                          .builder()
                          .series(id.getSeries())
                          .season(id.getSeason())
                          .id(id.getId())
                          .givenName(data.getGivenName())
                          .familyName(data.getFamilyName())
                          .code(data.getCode())
                          .dateOfBirth(LocalDate.parse(data.getDateOfBirth()))
                          .permanentNumber(data.getPermanentNumber())
                          .nationality(data.getNationality())
                          .url(data.getUrl())
                          .build();


}
