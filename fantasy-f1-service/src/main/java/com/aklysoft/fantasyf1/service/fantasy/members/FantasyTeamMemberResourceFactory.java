package com.aklysoft.fantasyf1.service.fantasy.members;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.container.ResourceContext;

@ApplicationScoped
public class FantasyTeamMemberResourceFactory {

  private final FantasyTeamMemberService fantasyTeamMemberService;

  public FantasyTeamMemberResourceFactory(FantasyTeamMemberService fantasyTeamMemberService) {
    this.fantasyTeamMemberService = fantasyTeamMemberService;
  }

  public FantasyTeamMemberResource create(ResourceContext resourceContext) {
    FantasyTeamMemberResource resource = resourceContext.getResource(FantasyTeamMemberResource.class);
    resource.setFantasyTeamMemberService(fantasyTeamMemberService);
    return resource;
  }
}
