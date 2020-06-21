package com.aklysoft.fantasyf1.service.fantasy.definitions;

import com.aklysoft.fantasyf1.service.fantasy.definitions.series.FantasySeriesService;
import com.aklysoft.fantasyf1.service.fantasy.definitions.series.FantasySeriesType;
import com.aklysoft.fantasyf1.service.fantasy.members.FantasyTeamMemberCategory;
import com.aklysoft.fantasyf1.service.fantasy.members.FantasyTeamMemberCategoryService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.util.List;

import static com.aklysoft.fantasyf1.service.fantasy.definitions.FantasyDefinitionMappers.createFantasySeasonViewItemId;
import static com.aklysoft.fantasyf1.service.fantasy.definitions.FantasyDefinitionMappers.toFantasySeasonViewItem;

@Path("/api/v1/fantasy/defs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FantasyDefinitionsResource {

  private final FantasySeriesService fantasySeriesService;
  private final FantasyDefinitionService fantasyDefinitionService;
  private final FantasyTeamMemberCategoryService fantasyTeamMemberCategoryService;

  public FantasyDefinitionsResource(FantasySeriesService fantasySeriesService, FantasyDefinitionService fantasyDefinitionService,
                                    FantasyTeamMemberCategoryService fantasyTeamMemberCategoryService) {
    this.fantasySeriesService = fantasySeriesService;
    this.fantasyDefinitionService = fantasyDefinitionService;
    this.fantasyTeamMemberCategoryService = fantasyTeamMemberCategoryService;
  }

  @GET
  @Path("/series")
  public FantasySeriesDefinition getFantasySeries() {
    return FantasySeriesDefinition
            .builder()
            .fantasySeriesList(fantasySeriesService.getFantasySeries())
            .selectedFantasySeriesId(FantasySeriesType.FORMULA_1.id)
            .build();
  }

  @GET
  @Path("/series/{series}/seasons")
  public FantasySeasonDefinition getFantasySeries(@PathParam("series") String series) {
    return FantasySeasonDefinition
            .builder()
            .series(series)
            .fantasySeasons(toFantasySeasonViewItem(series, fantasyDefinitionService.getFantasySeasons(series)))
            .selectedFantasySeasonId(createFantasySeasonViewItemId(series, fantasyDefinitionService.getCurrentSeason(series)))
            .build();
  }

  @GET
  @Path("/series/{series}/seasons/{season}/races")
  public FantasyRaceDefinition getFantasyRaces(@PathParam("series") String series, @PathParam("season") Integer season) {
    return fantasyDefinitionService.getFantasyRaceDefinition(series, season);
  }

  @GET
  @Path("/fantasy/team/member/categories")
  public List<FantasyTeamMemberCategory> getFantasyTeamMemberCategories() {
    return fantasyTeamMemberCategoryService.getFantasyTeamMemberCategories();
  }

}
