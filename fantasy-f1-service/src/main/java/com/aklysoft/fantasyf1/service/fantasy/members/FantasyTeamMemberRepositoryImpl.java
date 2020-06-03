package com.aklysoft.fantasyf1.service.fantasy.members;

import com.aklysoft.fantasyf1.service.core.orm.GeneralRepositoryImpl;
import com.aklysoft.fantasyf1.service.fantasy.teams.FantasyTeamPK;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class FantasyTeamMemberRepositoryImpl extends GeneralRepositoryImpl<FantasyTeamMember, FantasyTeamMemberPK> implements FantasyTeamMemberRepository {
  public FantasyTeamMemberRepositoryImpl() {
    super(FantasyTeamMember.class, FantasyTeamMemberPK.class);
  }

  @Override
  public List<FantasyTeamMember> getFantasyTeamMembers(FantasyTeamPK teamId) {
    return entityManager.createQuery(
            "select f from FantasyTeamMember f " +
                    "where f.series = ?1 and f.season = ?2 and f.userName = ?3",
            FantasyTeamMember.class)
            .setParameter(1, teamId.getSeries())
            .setParameter(2, teamId.getSeason())
            .setParameter(3, teamId.getUserName())
            .getResultList();
  }

  @Override
  public List<FantasyTeamMember> getFantasyTeamMembers(FantasyTeamPK teamId, int race) {
    return entityManager.createQuery(
            "select f from FantasyTeamMember f " +
                    "where f.series = ?1 and f.season = ?2 and f.userName = ?3 and f.round = ?4",
            FantasyTeamMember.class)
            .setParameter(1, teamId.getSeries())
            .setParameter(2, teamId.getSeason())
            .setParameter(3, teamId.getUserName())
            .setParameter(4, race)
            .getResultList();
  }
}
