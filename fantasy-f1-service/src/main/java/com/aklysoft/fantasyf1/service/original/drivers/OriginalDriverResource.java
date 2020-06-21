package com.aklysoft.fantasyf1.service.original.drivers;

import com.aklysoft.fantasyf1.service.original.OriginalSubResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static com.aklysoft.fantasyf1.service.original.drivers.OriginalDriverMappers.toOriginalDriverViewItems;

@Produces(MediaType.APPLICATION_JSON)
public class OriginalDriverResource implements OriginalSubResource {

  private final String series;
  private final OriginalDriverService originalDriverService;

  public OriginalDriverResource(String series, OriginalDriverService originalDriverService) {
    this.series = series;
    this.originalDriverService = originalDriverService;
  }

  @GET
  @Path("/{season}")
  public List<OriginalDriverViewItem> getDrivers(@PathParam("season") int season) {
    return toOriginalDriverViewItems(originalDriverService.getDrivers(series, season));
  }

}
