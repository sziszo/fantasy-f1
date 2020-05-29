package com.aklysoft.fantasyf1.service.fantasy.teams;

import com.aklysoft.fantasyf1.service.core.orm.GeneralRepositoryImpl;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.List;

@ApplicationScoped
public class FantasyTeamRepositoryImpl extends GeneralRepositoryImpl<FantasyTeam, FantasyTeamPK> implements FantasyTeamRepository {
  public FantasyTeamRepositoryImpl() {
    super(FantasyTeam.class, FantasyTeamPK.class);
  }

  @Override
  public List<FantasyTeam> findAllBySeriesAndSeason(String series, int season) {
    return entityManager.createQuery("select f from FantasyTeam f where f.series = ?1 and f.season = ?2", FantasyTeam.class)
            .setParameter(1, series)
            .setParameter(2, season)
            .getResultList();
  }

  @Override
  public boolean isExistName(String name, String userName) {
    try {
      final FantasyTeam result = entityManager.createQuery("select f from FantasyTeam f where f.name = ?1 and f.userName <> ?2 ", FantasyTeam.class)
              .setParameter(1, name)
              .setParameter(2, userName)
              .getSingleResult();
      return true;
    } catch (NoResultException | NonUniqueResultException e) {
      return false;
    }
  }
}
