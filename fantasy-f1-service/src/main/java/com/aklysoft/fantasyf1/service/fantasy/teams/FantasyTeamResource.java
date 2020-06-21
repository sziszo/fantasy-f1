package com.aklysoft.fantasyf1.service.fantasy.teams;

import com.aklysoft.fantasyf1.service.fantasy.members.FantasyTeamMemberResource;
import com.aklysoft.fantasyf1.service.users.UserIdHolder;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static com.aklysoft.fantasyf1.service.fantasy.teams.FantasyTeamMappers.toFantasyTeamViewItem;

@Path("/api/v1/fantasy/{series}/teams")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FantasyTeamResource {

  @PathParam("series")
  private String series;

  private final FantasyTeamService fantasyTeamService;
  private final UserIdHolder userIdHolder;

  @Context
  private ResourceContext resourceContext;

  public FantasyTeamResource(FantasyTeamService fantasyTeamService, UserIdHolder userIdHolder) {
    this.fantasyTeamService = fantasyTeamService;
    this.userIdHolder = userIdHolder;
  }

  @GET
  @Path("/{season}/all")
  public List<FantasyTeamViewItem> getAllFantasyTeams(@PathParam("season") int season) {
    return toFantasyTeamViewItem(fantasyTeamService.getAllFantasyTeams(series, season));
  }

  @GET
  @RolesAllowed("user")
  @Path("/{season}")
  public FantasyTeamViewItem getFantasyTeam(@PathParam("season") int season) {
    return toFantasyTeamViewItem(fantasyTeamService.getFantasyTeam(series, season, userIdHolder.getCurrentUserName()));
  }

  @GET
  @RolesAllowed("admin")
  @Path("/{season}/admin/{username}")
  public FantasyTeam getFantasyTeam(@PathParam("season") int season, @PathParam("username") String username) {
    return fantasyTeamService.getFantasyTeam(series, season, username);
  }

  @PUT
  @RolesAllowed("user")
  public FantasyTeam createFantasyTeam(String name) {
    String username = userIdHolder.getCurrentUserName();
    return fantasyTeamService.createFantasyTeam(
            NewFantasyTeam
                    .builder()
                    .series(series)
                    .username(username)
                    .name(StringUtils.trim(name))
                    .creator(username)
                    .build()
    );
  }

  @PUT
  @RolesAllowed("admin")
  @Path("/{season}/admin/{username}")
  public FantasyTeam createFantasyTeam(@PathParam("season") int season, @PathParam("username") String username, String name) {
    return fantasyTeamService.createFantasyTeam(
            NewFantasyTeam
                    .builder()
                    .series(series)
                    .season(season)
                    .username(username)
                    .name(StringUtils.trim(name))
                    .creator(userIdHolder.getCurrentUserName())
                    .build()
    );
  }

  @POST
  @RolesAllowed("user")
  public FantasyTeam updateFantasyTeam(String name) {
    String username = userIdHolder.getCurrentUserName();
    return fantasyTeamService.updateFantasyTeam(
            ModifyFantasyTeam
                    .builder()
                    .username(username)
                    .series(series)
                    .name(StringUtils.trim(name))
                    .modifier(username)
                    .build()
    );
  }

  @POST
  @RolesAllowed("admin")
  @Path("/{season}/admin/{username}")
  public FantasyTeam updateFantasyTeam(@PathParam("season") int season, @PathParam("username") String username, String name) {
    return fantasyTeamService.updateFantasyTeam(
            ModifyFantasyTeam
                    .builder()
                    .username(username)
                    .series(series)
                    .season(season)
                    .name(StringUtils.trim(name))
                    .modifier(userIdHolder.getCurrentUserName())
                    .build()
    );
  }

  @Path("/{season}/members")
  public FantasyTeamMemberResource fantasyTeamMemberResource() {
    return resourceContext.getResource(FantasyTeamMemberResource.class);
  }
}
