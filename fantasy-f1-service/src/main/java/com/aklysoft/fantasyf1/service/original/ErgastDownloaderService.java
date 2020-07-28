package com.aklysoft.fantasyf1.service.original;

import com.aklysoft.fantasyf1.service.original.constructors.model.EConstructorData;
import com.aklysoft.fantasyf1.service.original.drivers.model.EDriverData;
import com.aklysoft.fantasyf1.service.original.races.model.ERaceData;
import com.aklysoft.fantasyf1.service.original.standings.model.EConstructorStandingData;
import com.aklysoft.fantasyf1.service.original.standings.model.EDriverStandingData;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(baseUri = "http://ergast.com/api")
@ApplicationScoped
public interface ErgastDownloaderService {

  /*
   * Races
   */

  @GET
  @Path("/{series}/{year}.json")
  EResponse<ERaceData> getRaces(@PathParam("series") String series, @PathParam("year") int year,
                                @QueryParam("offset") int offset, @QueryParam("limit") int limit);

  @GET
  @Path("/{series}/{year}/results.json")
  EResponse<ERaceData> getRaceResults(@PathParam("series") String series, @PathParam("year") int year,
                                      @QueryParam("offset") int offset, @QueryParam("limit") int limit);

  @GET
  @Path("/{series}/{year}/{race}/results.json")
  EResponse<ERaceData> getRaceResult(@PathParam("series") String series, @PathParam("year") int year, @PathParam("race") int race,
                                     @QueryParam("offset") int offset, @QueryParam("limit") int limit);

  @GET
  @Path("/{series}/current/last/results.json")
  EResponse<ERaceData> getCurrentRaceResult(@PathParam("series") String series, @QueryParam("offset") int offset, @QueryParam("limit") int limit);

  /*
   * Drivers
   */

  @GET
  @Path("/{series}/{year}/drivers.json")
  EResponse<EDriverData> getDrivers(@PathParam("series") String series, @PathParam("year") int year,
                                    @QueryParam("offset") int offset, @QueryParam("limit") int limit);

  @GET
  @Path("/{series}/{year}/constructors.json")
  EResponse<EConstructorData> getConstructors(@PathParam("series") String series, @PathParam("year") int year,
                                              @QueryParam("offset") int offset, @QueryParam("limit") int limit);

  @GET
  @Path("/{series}/{year}/constructors/{constructor}/drivers.json")
  EResponse<EDriverData> getConstructorDrivers(@PathParam("series") String series, @PathParam("year") int year,
                                               @PathParam("constructor") String constructor);

  @GET
  @Path("/{series}/{year}/driverStandings.json")
  EResponse<EDriverStandingData> getDriverStandings(@PathParam("series") String series, @PathParam("year") int year);

  @GET
  @Path("/{series}/{year}/{round}/driverStandings.json")
  EResponse<EDriverStandingData> getDriverStandings(@PathParam("series") String series, @PathParam("year") int year, @PathParam("round") int round);

  @GET
  @Path("/{series}/{year}/constructorStandings.json")
  EResponse<EConstructorStandingData> getConstructorStandings(@PathParam("series") String series, @PathParam("year") int year);

  @GET
  @Path("/{series}/{year}/{round}/constructorStandings.json")
  EResponse<EConstructorStandingData> getConstructorStandings(@PathParam("series") String series, @PathParam("year") int year, @PathParam("round") int round);
}
