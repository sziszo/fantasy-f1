package com.aklysoft.fantasyf1.service.original.standings.constructors;

import com.aklysoft.fantasyf1.service.original.ErgastDownloaderService;
import com.aklysoft.fantasyf1.service.original.OriginalService;
import com.aklysoft.fantasyf1.service.original.standings.OriginalStandingMappers;
import com.aklysoft.fantasyf1.service.original.standings.model.EConstructorStanding;
import com.aklysoft.fantasyf1.service.original.standings.model.EConstructorStandingList;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

@ApplicationScoped
public class OriginalConstructorStandingService extends OriginalService<OriginalConstructorStanding, OriginalConstructorStandingPK> {

  private final ErgastDownloaderService downloaderService;

  @Inject
  public OriginalConstructorStandingService(OriginalConstructorStandingRepository originalConstructorStandingRepository,
                                            @RestClient ErgastDownloaderService downloaderService) {
    super(originalConstructorStandingRepository);
    this.downloaderService = downloaderService;
  }

  @Transactional
  public List<OriginalConstructorStanding> getConstructorStandings(String series, int season) {
    return getConstructorStandings(series, season, null);
  }

  @Transactional
  public List<OriginalConstructorStanding> getConstructorStandings(String series, int season, Integer round) {

    Supplier<Stream<EConstructorStanding>> supplier = () ->
            Optional.ofNullable(round)
                    .map(rnd -> downloaderService.getConstructorStandings(series, season, rnd))
                    .orElseGet(() -> downloaderService.getConstructorStandings(series, season))
                    .getData()
                    .getStandingTable()
                    .getStandingLists()
                    .stream()
                    .map(EConstructorStandingList::getConstructorStandings)
                    .findFirst()
                    .orElseThrow(() -> new OriginalConstructorStandingNotExistException(series, season, round))
                    .stream();

    return super.getAllBySeason(series, season, supplier,
            OriginalStandingMappers.getConstructorStandingIdMapper(series, season),
            OriginalStandingMappers.constructorStandingMapper
    );

  }
}
