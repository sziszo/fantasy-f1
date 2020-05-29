package com.aklysoft.fantasyf1.service.fantasy.members;

import com.aklysoft.fantasyf1.service.fantasy.teams.FantasyTeamPK;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FantasyTeamMemberResource {

  @PathParam("series")
  private String series;

  @PathParam("season")
  int season;

  @PathParam("username")
  String username;

  private FantasyTeamMemberService fantasyTeamMemberService;


  public void setFantasyTeamMemberService(FantasyTeamMemberService fantasyTeamMemberService) {
    this.fantasyTeamMemberService = fantasyTeamMemberService;
  }

  @GET
  public List<FantasyTeamMember> getFantasyTeamMembers() {
    return fantasyTeamMemberService.getFantasyTeamMembers(
            FantasyTeamPK
                    .builder()
                    .series(series)
                    .season(season)
                    .userName(username)
                    .build()
    );
  }

  @GET
  @Path("/{race}")
  public List<FantasyTeamMember> getFantasyTeamMembers(@PathParam("race") int race) {
    return fantasyTeamMemberService.getFantasyTeamMembers(
            FantasyTeamPK
                    .builder()
                    .series(series)
                    .season(season)
                    .userName(username)
                    .build(),
            race
    );
  }

  @POST
  public FantasyTeamMember setTeamMember(ModifyFantasyTeamMember modifyFantasyTeamMember) {

    return fantasyTeamMemberService.setTeamMember(
            FantasyTeamPK
                    .builder()
                    .series(series)
                    .season(season)
                    .userName(username)
                    .build(),
            modifyFantasyTeamMember);
  }

}
