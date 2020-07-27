package com.aklysoft.fantasyf1.service.fantasy.definitions.prices;

import com.aklysoft.fantasyf1.service.core.orm.GeneralRepositoryImpl;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class FantasyPositionPriceDefinitionRepositoryImpl extends GeneralRepositoryImpl<FantasyPositionPriceDefinition, FantasyPositionPriceDefinitionPK>
        implements FantasyPositionPriceDefinitionRepository {

  public FantasyPositionPriceDefinitionRepositoryImpl() {
    super(FantasyPositionPriceDefinition.class, FantasyPositionPriceDefinitionPK.class);
  }

  @Override
  public List<FantasyPositionPriceDefinition> getFantasyPositionPriceDefinitions(String series, int season, FantasyPositionPriceDefinitionType type) {
    return entityManager.createQuery("select d from FantasyPositionPriceDefinition d where d.series = ?1 and d.season = ?2 and d.type = ?3",
            FantasyPositionPriceDefinition.class)
            .setParameter(1, series)
            .setParameter(2, season)
            .setParameter(3, type)
            .getResultList();
  }
}
