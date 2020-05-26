package com.aklysoft.fantasyf1.service.original;

import com.aklysoft.fantasyf1.service.original.constructors.OriginalConstructorResource;
import com.aklysoft.fantasyf1.service.original.drivers.OriginalDriverResource;
import com.aklysoft.fantasyf1.service.original.races.OriginalRaceResource;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/api/v1/original")
public class OriginalResource {

  private final OriginalSubResourceFactory originalSubResourceFactory;

  public OriginalResource(OriginalSubResourceFactory originalSubResourceFactory) {
    this.originalSubResourceFactory = originalSubResourceFactory;
  }


  @Path("/{series}/races")
  public OriginalRaceResource originalRaceResource(@PathParam("series") String series) {
    return (OriginalRaceResource) originalSubResourceFactory.create(OriginalSubResource.OriginalSubResourceType.RACE, series);
  }

  @Path("/{series}/drivers")
  public OriginalDriverResource originalDriverResource(@PathParam("series") String series) {
    return (OriginalDriverResource) originalSubResourceFactory.create(OriginalSubResource.OriginalSubResourceType.DRIVER, series);
  }

  @Path("/{series}/constructors")
  public OriginalConstructorResource originalConstructorResource(@PathParam("series") String series) {
    return (OriginalConstructorResource) originalSubResourceFactory.create(OriginalSubResource.OriginalSubResourceType.CONSTRUCTOR, series);
  }

}
