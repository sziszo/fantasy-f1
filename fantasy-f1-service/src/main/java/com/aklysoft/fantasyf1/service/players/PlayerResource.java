package com.aklysoft.fantasyf1.service.players;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api/v1/players")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "player")
@ApplicationScoped
public class PlayerResource {

  private final PlayerService playerService;

  public PlayerResource(PlayerService playerService) {
    this.playerService = playerService;
  }

  @GET
  public List<PlayerView> getPlayers() {
    return PlayerViewMapper.toPlayerViews(playerService.getAllPlayers());
  }

  @GET
  @Path("/{username}")
  public PlayerView getPlayer(@PathParam("username") String username) {
    return PlayerViewMapper.toPlayerView(playerService.getPlayer(username));
  }

  @POST
  public Response createPlayer(NewPlayer newPlayer) {
    return Response.status(Response.Status.CREATED).entity(playerService.create(newPlayer)).build();
  }

  @DELETE
  @Path("/{username}")
  public Response deletePlayer(@PathParam("username") String username) {
    playerService.delete(username);
    return Response.accepted().build();
  }


}
