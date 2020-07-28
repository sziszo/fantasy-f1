package com.aklysoft.fantasyf1.service.original.standings.drivers;

import com.aklysoft.fantasyf1.service.original.ErgastDownloaderService;
import com.aklysoft.fantasyf1.service.original.OriginalService;
import com.aklysoft.fantasyf1.service.original.standings.OriginalStandingMappers;
import com.aklysoft.fantasyf1.service.original.standings.model.EDriverStanding;
import com.aklysoft.fantasyf1.service.original.standings.model.EDriverStandingList;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

@ApplicationScoped
public class OriginalDriverStandingService extends OriginalService<OriginalDriverStanding, OriginalDriverStandingPK> {

  private final ErgastDownloaderService downloaderService;

  @Inject
  public OriginalDriverStandingService(OriginalDriverStandingRepository originalDriverStandingRepository,
                                       @RestClient ErgastDownloaderService downloaderService) {
    super(originalDriverStandingRepository);
    this.downloaderService = downloaderService;
  }

  @Transactional
  public List<OriginalDriverStanding> getDriverStandings(String series, int season) {
    return getDriverStandings(series, season, null);
  }

  @Transactional
  public List<OriginalDriverStanding> getDriverStandings(String series, int season, Integer round) {

    Supplier<Stream<EDriverStanding>> supplier = () ->
            Optional.ofNullable(round)
                    .map(rnd -> downloaderService.getDriverStandings(series, season, rnd))
                    .orElseGet(() -> downloaderService.getDriverStandings(series, season))
                    .getData()
                    .getStandingTable()
                    .getStandingLists()
                    .stream()
                    .map(EDriverStandingList::getDriverStandings)
                    .findFirst()
                    .orElseThrow(() -> new OriginalDriverStandingNotExistException(series, season, round))
                    .stream();

    return super.getAllBySeason(series, season, supplier,
            OriginalStandingMappers.getDriverStandingIdMapper(series, season),
            OriginalStandingMappers.driverStandingMapper
    );

  }



}
