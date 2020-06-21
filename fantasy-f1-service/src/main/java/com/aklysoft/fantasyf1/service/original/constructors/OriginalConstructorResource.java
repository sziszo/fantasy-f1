package com.aklysoft.fantasyf1.service.original.constructors;

import com.aklysoft.fantasyf1.service.original.OriginalSubResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static com.aklysoft.fantasyf1.service.original.constructors.OriginalConstructorMappers.toOriginalConstructorViewItems;

@Produces(MediaType.APPLICATION_JSON)
public class OriginalConstructorResource implements OriginalSubResource {

  private final String series;
  private final OriginalConstructorService originalConstructorService;

  public OriginalConstructorResource(String series, OriginalConstructorService originalConstructorService) {
    this.series = series;
    this.originalConstructorService = originalConstructorService;
  }

  @GET
  @Path("/{season}")
  public List<OriginalConstructorVIewItem> getConstructors(@PathParam("season") int season) {
    return toOriginalConstructorViewItems(originalConstructorService.getConstructors(series, season));
  }

}
