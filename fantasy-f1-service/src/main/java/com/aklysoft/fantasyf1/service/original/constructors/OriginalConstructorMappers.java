package com.aklysoft.fantasyf1.service.original.constructors;

import com.aklysoft.fantasyf1.service.original.constructors.model.EConstructor;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.aklysoft.fantasyf1.service.core.utils.StringUtils.SEPARATOR;
import static java.util.stream.Collectors.toList;

public class OriginalConstructorMappers {

  public static Function<EConstructor, OriginalConstructorPK> getConstructorIdMapper(String series, int year) {
    return data -> OriginalConstructorPK
            .builder()
            .series(series)
            .season(year)
            .id(data.getConstructorId())
            .build();
  }

  public static BiFunction<EConstructor, OriginalConstructorPK, OriginalConstructor> constructorMapper =
          (data, id) ->
                  OriginalConstructor
                          .builder()
                          .series(id.getSeries())
                          .season(id.getSeason())
                          .id(id.getId())
                          .name(data.getName())
                          .nationality(data.getNationality())
                          .url(data.getUrl())
                          .build();


  public static String toConstructorViewItemId(String series, int season, String constructorId) {
    return "cr" + SEPARATOR + series + SEPARATOR + season + SEPARATOR + constructorId;
  }

  public static List<OriginalConstructorVIewItem> toOriginalConstructorViewItems(List<OriginalConstructor> constructors) {
    return toOriginalConstructorViewItems(constructors.stream()).collect(toList());
  }

  public static Stream<OriginalConstructorVIewItem> toOriginalConstructorViewItems(Stream<OriginalConstructor> constructors) {
    return constructors.map(OriginalConstructorMappers::toOriginalConstructorVIewItem);
  }

  public static OriginalConstructorVIewItem toOriginalConstructorVIewItem(OriginalConstructor constructor) {
    final String series = constructor.getSeries();
    final int season = constructor.getSeason();
    final String constructorId = constructor.getId();

    return OriginalConstructorVIewItem
            .builder()
            .id(toConstructorViewItemId(series, season, constructorId))
            .series(series)
            .season(season)
            .constructorId(constructorId)
            .name(constructor.getName())
            .nationality(constructor.getNationality())
            .url(constructor.getUrl())
            .build();
  }
}
