package com.aklysoft.fantasyf1.service.original.races;

import com.aklysoft.fantasyf1.service.original.OriginalSubResource;
import com.aklysoft.fantasyf1.service.original.OriginalSubResourceFactory;
import com.aklysoft.fantasyf1.service.original.results.OriginalRaceResultResource;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OriginalRaceResourceFactory {

  private final OriginalRaceService originalRaceService;
  private final OriginalSubResourceFactory originalSubResourceFactory;

  public OriginalRaceResourceFactory(OriginalRaceService originalRaceService,
                                     OriginalSubResourceFactory originalSubResourceFactory) {
    this.originalRaceService = originalRaceService;
    this.originalSubResourceFactory = originalSubResourceFactory;
  }


  public OriginalRaceResource create(String series) {
    return new OriginalRaceResource(series, originalRaceService,
            (OriginalRaceResultResource) originalSubResourceFactory.create(OriginalSubResource.OriginalSubResourceType.RACE_RESULT, series));
  }
}
