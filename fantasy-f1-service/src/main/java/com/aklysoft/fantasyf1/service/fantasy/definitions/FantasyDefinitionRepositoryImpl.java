package com.aklysoft.fantasyf1.service.fantasy.definitions;

import com.aklysoft.fantasyf1.service.core.orm.GeneralRepositoryImpl;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import java.util.List;

@ApplicationScoped
public class FantasyDefinitionRepositoryImpl extends GeneralRepositoryImpl<FantasyDefinition, FantasyDefinitionPK> implements FantasyDefinitionRepository {
  public FantasyDefinitionRepositoryImpl() {
    super(FantasyDefinition.class, FantasyDefinitionPK.class);
  }


  @Override
  public Integer getCurrentSeason(String series) {
    return entityManager.createQuery("select max(d.season) from FantasyDefinition d where d.series = ?1", Integer.class)
            .setParameter(1, series)
            .getSingleResult();
  }

  @Override
  public Integer getNextRace(String series, int season) {
    try {
      return entityManager.createQuery("select d.nextRace from FantasyDefinition d where d.series = ?1 and d.season = ?2", Integer.class)
              .setParameter(1, series)
              .setParameter(2, season)
              .getSingleResult();
    } catch (NoResultException ex) {
      return null;
    }
  }

  @Override
  public List<Integer> getSeasons(String series) {
    return entityManager.createQuery("select distinct d.season from FantasyDefinition d where d.series = ?1", Integer.class)
            .setParameter(1, series)
            .getResultList();
  }

  @Override
  public Long getInitialMoney(String series, int season) {
    try {
      return entityManager.createQuery("select d.initialMoney from FantasyDefinition d where d.series = ?1 and d.season = ?2", Long.class)
              .setParameter(1, series)
              .setParameter(2, season)
              .getSingleResult();
    } catch (NoResultException ex) {
      return null;
    }
  }

  @Override
  public Integer getInitialDriverPrice(String series, int season) {
    try {
      return entityManager.createQuery("select d.initialDriverPrice from FantasyDefinition d where d.series = ?1 and d.season = ?2", Integer.class)
              .setParameter(1, series)
              .setParameter(2, season)
              .getSingleResult();
    } catch (NoResultException ex) {
      return null;
    }
  }

  @Override
  public Integer getInitialConstructorPrice(String series, int season) {
    try {
      return entityManager.createQuery("select d.initialConstructorPrice from FantasyDefinition d where d.series = ?1 and d.season = ?2", Integer.class)
              .setParameter(1, series)
              .setParameter(2, season)
              .getSingleResult();
    } catch (NoResultException ex) {
      return null;
    }
  }
}
