package com.aklysoft.fantasyf1.service.original.constructors;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OriginalConstructorResourceFactory {

  private final OriginalConstructorService originalConstructorService;

  public OriginalConstructorResourceFactory(OriginalConstructorService originalConstructorService) {
    this.originalConstructorService = originalConstructorService;
  }

  public OriginalConstructorResource create(String series) {
    return new OriginalConstructorResource(series, originalConstructorService);
  }

}
