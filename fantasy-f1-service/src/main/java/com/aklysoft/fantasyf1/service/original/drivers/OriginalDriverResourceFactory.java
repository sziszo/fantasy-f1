package com.aklysoft.fantasyf1.service.original.drivers;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OriginalDriverResourceFactory {

  private final OriginalDriverService originalDriverService;

  public OriginalDriverResourceFactory(OriginalDriverService originalDriverService) {
    this.originalDriverService = originalDriverService;
  }

  public OriginalDriverResource create(String series) {
    return new OriginalDriverResource(series, originalDriverService);
  }
}
