package com.aklysoft.fantasyf1.service.original.drivers;

import com.aklysoft.fantasyf1.service.original.drivers.model.EDriver;

import java.time.LocalDate;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.aklysoft.fantasyf1.service.core.utils.StringUtils.SEPARATOR;
import static com.aklysoft.fantasyf1.service.original.constructors.OriginalConstructorMappers.toConstructorViewItemId;
import static java.util.stream.Collectors.toList;

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

  public static String toDriverViewItemId(String series, int season, String driverId) {
    return "dr" + SEPARATOR + series + SEPARATOR + season + SEPARATOR + driverId;
  }


  public static List<OriginalDriverViewItem> toOriginalDriverViewItems(List<OriginalDriver> drivers) {
    return toOriginalDriverViewItems(drivers.stream()).collect(toList());
  }

  public static Stream<OriginalDriverViewItem> toOriginalDriverViewItems(Stream<OriginalDriver> drivers) {
    return drivers.map(OriginalDriverMappers::toOriginalDriverViewItem);
  }

  public static OriginalDriverViewItem toOriginalDriverViewItem(OriginalDriver driver) {
    final String series = driver.getSeries();
    final int season = driver.getSeason();
    final String driverId = driver.getId();

    return OriginalDriverViewItem
            .builder()
            .id(toDriverViewItemId(series, season, driverId))
            .series(series)
            .season(season)
            .driverId(driverId)
            .constructorId(toConstructorViewItemId(series, season, driver.getConstructorId()))
            .name(getDriverDisplayName(driver))
            .url(driver.getUrl())
            .permanentNumber(driver.getPermanentNumber())
            .build();
  }

  private static String getDriverDisplayName(OriginalDriver driver) {
    return driver.getGivenName() + " " + driver.getFamilyName();
  }
}
