package com.aklysoft.fantasyf1.service.fantasy.definitions.series;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class FantasySeriesService {

  private final FantasySeriesRepository fantasySeriesRepository;

  public FantasySeriesService(FantasySeriesRepository fantasySeriesRepository) {
    this.fantasySeriesRepository = fantasySeriesRepository;
  }

  @Transactional
  public List<FantasySeries> getFantasySeries() {
    return fantasySeriesRepository.findAll();
  }
}
