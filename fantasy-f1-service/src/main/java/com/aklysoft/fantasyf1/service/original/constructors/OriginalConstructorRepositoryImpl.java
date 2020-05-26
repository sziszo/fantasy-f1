package com.aklysoft.fantasyf1.service.original.constructors;

import com.aklysoft.fantasyf1.service.original.OriginalRepositoryImpl;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OriginalConstructorRepositoryImpl extends OriginalRepositoryImpl<OriginalConstructor, OriginalConstructorPK> implements OriginalConstructorRepository {
  public OriginalConstructorRepositoryImpl() {
    super(OriginalConstructor.class, OriginalConstructorPK.class);
  }

}
