package com.aklysoft.fantasyf1.service.original.races;

import com.aklysoft.fantasyf1.service.original.OriginalSubResource;
import com.aklysoft.fantasyf1.service.original.results.OriginalRaceResultResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
public class OriginalRaceResource implements OriginalSubResource {

  private final String series;
  private final OriginalRaceService originalRaceService;
  private final OriginalRaceResultResource originalRaceResultResource;

  public OriginalRaceResource(String series, OriginalRaceService originalRaceService,
                              OriginalRaceResultResource originalRaceResultResource) {
    this.series = series;
    this.originalRaceService = originalRaceService;
    this.originalRaceResultResource = originalRaceResultResource;
  }

  @GET
  @Path("/{year}")
  public List<OriginalRace> getRaces(@PathParam("year") int year) {
    return originalRaceService.getRaces(series, year);
  }

  @Path("/")
  public OriginalRaceResultResource originalRaceResultResource() {
    return originalRaceResultResource;
  }

}
