package com.aklysoft.fantasyf1.service.original;

import com.aklysoft.fantasyf1.service.core.orm.GeneralRepositoryImpl;

import java.util.List;

public abstract class OriginalRepositoryImpl<T, ID> extends GeneralRepositoryImpl<T, ID> implements OriginalRepository<T, ID> {

  public OriginalRepositoryImpl(Class<T> entityClazz, Class<ID> idClass) {
    super(entityClazz, idClass);
  }

  @Override
  public List<T> findAllBySeason(String series, int year) {
    return this.entityManager
            .createQuery(" select r from " + getEntityClazz().getSimpleName() + " r where r.series = ?1 and r.season = ?2 ", getEntityClazz())
            .setParameter(1, series)
            .setParameter(2, year)
            .getResultList();
  }


}
