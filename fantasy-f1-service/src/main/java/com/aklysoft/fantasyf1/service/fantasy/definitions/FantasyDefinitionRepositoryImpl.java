package com.aklysoft.fantasyf1.service.fantasy.definitions;

import com.aklysoft.fantasyf1.service.core.orm.GeneralRepositoryImpl;

import javax.enterprise.context.ApplicationScoped;

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
    return entityManager.createQuery("select d.nextRace from FantasyDefinition d where d.series = ?1 and d.season = ?2", Integer.class)
            .setParameter(1, series)
            .setParameter(2, season)
            .getSingleResult();
  }
}
