package com.aklysoft.fantasyf1.service.original;

import com.aklysoft.fantasyf1.service.original.constructors.OriginalConstructorResourceFactory;
import com.aklysoft.fantasyf1.service.original.drivers.OriginalDriverResourceFactory;
import com.aklysoft.fantasyf1.service.original.races.OriginalRaceResourceFactory;
import com.aklysoft.fantasyf1.service.original.results.OriginalRaceResultResourceFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class OriginalSubResourceFactoryCacheImpl implements OriginalSubResourceFactory {

  @Data
  @AllArgsConstructor
  @EqualsAndHashCode
  static class OriginalSubResourceKey {
    private OriginalSubResource.OriginalSubResourceType type;
    private String series;
  }

  private Map<OriginalSubResourceKey, OriginalSubResource> cache = new HashMap<>();

  private final OriginalRaceResourceFactory originalRaceResourceFactory;
  private final OriginalRaceResultResourceFactory originalRaceResultResourceFactory;
  private final OriginalDriverResourceFactory originalDriverResourceFactory;
  private final OriginalConstructorResourceFactory originalConstructorResourceFactory;

  public OriginalSubResourceFactoryCacheImpl(OriginalRaceResourceFactory originalRaceResourceFactory,
                                             OriginalRaceResultResourceFactory originalRaceResultResourceFactory,
                                             OriginalDriverResourceFactory originalDriverResourceFactory, OriginalConstructorResourceFactory originalConstructorResourceFactory) {
    this.originalRaceResourceFactory = originalRaceResourceFactory;
    this.originalRaceResultResourceFactory = originalRaceResultResourceFactory;
    this.originalDriverResourceFactory = originalDriverResourceFactory;
    this.originalConstructorResourceFactory = originalConstructorResourceFactory;
  }

  @Override
  public synchronized OriginalSubResource create(OriginalSubResource.OriginalSubResourceType type, String series) {

    var key = new OriginalSubResourceKey(type, series);
    var resource = cache.get(key);
    if (resource == null) {
      switch (type) {
        case RACE:
          resource = originalRaceResourceFactory.create(series);
          break;
        case RACE_RESULT:
          resource = originalRaceResultResourceFactory.create(series);
          break;
        case DRIVER:
          resource = originalDriverResourceFactory.create(series);
          break;
        case CONSTRUCTOR:
          resource = originalConstructorResourceFactory.create(series);
          break;
      }
      cache.put(key, resource);
    }
    return resource;
  }
}
