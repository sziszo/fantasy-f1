package com.aklysoft.fantasyf1.service.original.results;

import com.aklysoft.fantasyf1.service.original.OriginalSubResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
public class OriginalRaceResultResource implements OriginalSubResource {

  private final String series;
  private final OriginalRaceResultService originalRaceService;

  public OriginalRaceResultResource(String series, OriginalRaceResultService originalRaceService) {
    this.series = series;
    this.originalRaceService = originalRaceService;
  }

  @GET
  @Path("/{year}/result")
  public List<OriginalRaceResult> getRaceResults(@PathParam("year") int year) {
    return originalRaceService.getRaceResults(series, year);
  }

  @GET
  @Path("/{year}/{race}/result")
  public List<OriginalRaceResult> getRaceResult(@PathParam("year") Integer year, @PathParam("race") Integer race) {
    return originalRaceService.getRaceResult(series, year, race);
  }

  @GET
  @Path("/current/last/result")
  public List<OriginalRaceResult> getRaceResult() {
    return originalRaceService.getCurrentRaceResult(series);
  }


}
