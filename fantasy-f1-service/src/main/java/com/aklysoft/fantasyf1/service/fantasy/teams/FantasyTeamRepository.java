package com.aklysoft.fantasyf1.service.fantasy.teams;

import com.aklysoft.fantasyf1.service.core.orm.GeneralRepository;

import java.util.List;

public interface FantasyTeamRepository extends GeneralRepository<FantasyTeam, FantasyTeamPK> {
  List<FantasyTeam> findAllBySeriesAndSeason(String series, int year);

  boolean isExistName(String name, String userName);
}
