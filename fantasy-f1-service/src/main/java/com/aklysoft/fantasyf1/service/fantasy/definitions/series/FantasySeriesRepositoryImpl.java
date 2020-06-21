package com.aklysoft.fantasyf1.service.fantasy.definitions.series;

import com.aklysoft.fantasyf1.service.core.orm.GeneralRepositoryImpl;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FantasySeriesRepositoryImpl extends GeneralRepositoryImpl<FantasySeries, String> implements FantasySeriesRepository {
  public FantasySeriesRepositoryImpl() {
    super(FantasySeries.class, String.class);
  }
}
