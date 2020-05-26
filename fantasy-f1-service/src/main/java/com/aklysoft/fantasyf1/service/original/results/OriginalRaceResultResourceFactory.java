package com.aklysoft.fantasyf1.service.original.results;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OriginalRaceResultResourceFactory {

  private final OriginalRaceResultService originalRaceResultService;

  public OriginalRaceResultResourceFactory(OriginalRaceResultService originalRaceResultService) {
    this.originalRaceResultService = originalRaceResultService;
  }

  public OriginalRaceResultResource create(String series) {
    return new OriginalRaceResultResource(series, originalRaceResultService);
  }
}
