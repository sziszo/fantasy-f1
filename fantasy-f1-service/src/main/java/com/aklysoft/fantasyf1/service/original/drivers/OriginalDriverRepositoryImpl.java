package com.aklysoft.fantasyf1.service.original.drivers;

import com.aklysoft.fantasyf1.service.original.OriginalRepositoryImpl;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OriginalDriverRepositoryImpl extends OriginalRepositoryImpl<OriginalDriver, OriginalDriverPK> implements OriginalDriverRepository {
  public OriginalDriverRepositoryImpl() {
    super(OriginalDriver.class, OriginalDriverPK.class);
  }
  
}
