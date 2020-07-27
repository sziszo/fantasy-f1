package com.aklysoft.fantasyf1.service.fantasy.members;

import com.aklysoft.fantasyf1.service.fantasy.teams.FantasyTeamPK;
import com.aklysoft.fantasyf1.service.users.UserIdHolder;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static com.aklysoft.fantasyf1.service.fantasy.members.FantasyTeamMemberMappers.toFantasyTeamMemberViewItem;
import static com.aklysoft.fantasyf1.service.fantasy.members.FantasyTeamMemberMappers.toFantasyTeamMemberViewItems;
import static com.aklysoft.fantasyf1.service.fantasy.teams.FantasyTeamMappers.toFantasyTeamViewItem;
import static java.util.Optional.ofNullable;


@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class FantasyTeamMemberResource {

  @PathParam("series")
  private String series;

  @PathParam("season")
  int season;

  private final FantasyTeamMemberService fantasyTeamMemberService;
  private final UserIdHolder userIdHolder;

  public FantasyTeamMemberResource(FantasyTeamMemberService fantasyTeamMemberService, UserIdHolder userIdHolder) {
    this.fantasyTeamMemberService = fantasyTeamMemberService;
    this.userIdHolder = userIdHolder;
  }

  @GET
  @RolesAllowed("user")
  public List<FantasyTeamMemberViewItem> getUserFantasyTeamMembers(@QueryParam("username") String username) {
    return toFantasyTeamMemberViewItems(
            fantasyTeamMemberService.getFantasyTeamMembers(
                    FantasyTeamPK
                            .builder()
                            .series(series)
                            .season(season)
                            .userName(ofNullable(username).orElseGet(userIdHolder::getCurrentUserName))
                            .build()));
  }

  @GET
  @RolesAllowed("admin")
  @Path("/admin/{username}")
  public List<FantasyTeamMember> getFantasyTeamMembers(@PathParam("username") String username) {
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
  @RolesAllowed("user")
  @Path("/{race}")
  public List<FantasyTeamMemberViewItem> getUserFantasyTeamMembers(@PathParam("race") int race, @QueryParam("username") String username) {
    return toFantasyTeamMemberViewItems(
            fantasyTeamMemberService.getFantasyTeamMembers(
                    FantasyTeamPK
                            .builder()
                            .series(series)
                            .season(season)
                            .userName(ofNullable(username).orElseGet(userIdHolder::getCurrentUserName))
                            .build(),
                    race));
  }

  @GET
  @RolesAllowed("admin")
  @Path("/{race}/admin/{username}")
  public List<FantasyTeamMember> getFantasyTeamMembers(@PathParam("username") String username, @PathParam("race") int race) {
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
  @RolesAllowed("user")
  public ModifyFantasyTeamMemberResponse setMyTeamMember(ModifyFantasyTeamMemberQuery modifyFantasyTeamMemberQuery) {
    String username = userIdHolder.getCurrentUserName();

    final FantasyTeamMember fantasyTeamMember = fantasyTeamMemberService.setTeamMember(
            FantasyTeamPK
                    .builder()
                    .series(series)
                    .season(season)
                    .userName(username)
                    .build(),
            null,
            modifyFantasyTeamMemberQuery);

    return ModifyFantasyTeamMemberResponse
            .builder()
            .fantasyTeamViewItem(toFantasyTeamViewItem(fantasyTeamMember.getTeam()))
            .fantasyTeamMemberViewItem(toFantasyTeamMemberViewItem(fantasyTeamMember))
            .build();
  }

  @POST
  @RolesAllowed("admin")
  @Path("/admin/{username}")
  public FantasyTeamMember setTeamMember(@PathParam("username") String username, @QueryParam("race") Integer race, ModifyFantasyTeamMemberQuery modifyFantasyTeamMemberQuery) {

    return fantasyTeamMemberService.setTeamMember(
            FantasyTeamPK
                    .builder()
                    .series(series)
                    .season(season)
                    .userName(username)
                    .build(),
            race,
            modifyFantasyTeamMemberQuery);
  }

  @DELETE
  @RolesAllowed("user")
  @Path("/{teamMemberCategoryType}")
  public DeleteFantasyTeamMemberResponse deleteMyTeamMember(@PathParam("teamMemberCategoryType") FantasyTeamMemberCategoryType teamMemberCategoryType) {
    String username = userIdHolder.getCurrentUserName();
    final FantasyTeamMember fantasyTeamMember = fantasyTeamMemberService.deleteTeamMember(
            FantasyTeamPK
                    .builder()
                    .series(series)
                    .season(season)
                    .userName(username)
                    .build(),
            null,
            teamMemberCategoryType);

    return DeleteFantasyTeamMemberResponse
            .builder()
            .fantasyTeamViewItem(toFantasyTeamViewItem(fantasyTeamMember.getTeam()))
            .fantasyTeamMemberViewItem(toFantasyTeamMemberViewItem(fantasyTeamMember))
            .build();
  }

  @DELETE
  @RolesAllowed("admin")
  @Path("/{teamMemberCategoryType}/admin/{username}")
  public FantasyTeamMember deleteTeamMember(@PathParam("username") String username, @PathParam("teamMemberCategoryType") FantasyTeamMemberCategoryType teamMemberCategoryType, @QueryParam("race") Integer race) {
    return fantasyTeamMemberService.deleteTeamMember(
            FantasyTeamPK
                    .builder()
                    .series(series)
                    .season(season)
                    .userName(username)
                    .build(),
            race,
            teamMemberCategoryType);
  }

}
