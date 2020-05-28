package com.aklysoft.fantasyf1.service.original.constructors;

import com.aklysoft.fantasyf1.service.original.constructors.model.EConstructor;

import java.util.function.BiFunction;
import java.util.function.Function;

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


}
