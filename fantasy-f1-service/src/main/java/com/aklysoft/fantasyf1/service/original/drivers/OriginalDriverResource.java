package com.aklysoft.fantasyf1.service.original.drivers;

import com.aklysoft.fantasyf1.service.original.OriginalSubResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
public class OriginalDriverResource implements OriginalSubResource {

  private final String series;
  private final OriginalDriverService originalDriverService;

  public OriginalDriverResource(String series, OriginalDriverService originalDriverService) {
    this.series = series;
    this.originalDriverService = originalDriverService;
  }

  @GET
  @Path("/{year}")
  public List<OriginalDriver> getDrivers(@PathParam("year") int year) {
    return originalDriverService.getDrivers(series, year);
  }

}
