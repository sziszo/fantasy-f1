package com.aklysoft.fantasyf1.service.fantasy.members;

import com.aklysoft.fantasyf1.service.core.orm.GeneralRepositoryImpl;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FantasyTeamMemberCategoryRepositoryImpl extends GeneralRepositoryImpl<FantasyTeamMemberCategory, FantasyTeamMemberCategoryType> implements FantasyTeamMemberCategoryRepository {
  public FantasyTeamMemberCategoryRepositoryImpl() {
    super(FantasyTeamMemberCategory.class, FantasyTeamMemberCategoryType.class);
  }
}
