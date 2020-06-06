package com.aklysoft.fantasyf1.service.fantasy.members;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class FantasyTeamMemberCategoryService {

  private final FantasyTeamMemberCategoryRepository fantasyTeamMemberCategoryRepository;

  public FantasyTeamMemberCategoryService(FantasyTeamMemberCategoryRepository fantasyTeamMemberCategoryRepository) {
    this.fantasyTeamMemberCategoryRepository = fantasyTeamMemberCategoryRepository;
  }

  public List<FantasyTeamMemberCategory> getFantasyTeamMemberCategories() {
    return fantasyTeamMemberCategoryRepository.findAll();
  }
}
