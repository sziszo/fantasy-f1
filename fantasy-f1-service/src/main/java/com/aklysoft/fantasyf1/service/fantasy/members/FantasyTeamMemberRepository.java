package com.aklysoft.fantasyf1.service.fantasy.members;

import com.aklysoft.fantasyf1.service.core.orm.GeneralRepository;
import com.aklysoft.fantasyf1.service.fantasy.teams.FantasyTeamPK;

import java.util.List;

public interface FantasyTeamMemberRepository extends GeneralRepository<FantasyTeamMember, FantasyTeamMemberPK> {
  List<FantasyTeamMember> getFantasyTeamMembers(FantasyTeamPK teamId);
  List<FantasyTeamMember> getFantasyTeamMembers(FantasyTeamPK teamId, int race);

}
