package com.aklysoft.fantasyf1.service.original.results;

import com.aklysoft.fantasyf1.service.core.AppConfiguration;
import com.aklysoft.fantasyf1.service.core.utils.StreamUtils;
import com.aklysoft.fantasyf1.service.original.ErgastDownloaderService;
import com.aklysoft.fantasyf1.service.original.OriginalService;
import com.aklysoft.fantasyf1.service.original.constructors.OriginalConstructor;
import com.aklysoft.fantasyf1.service.original.constructors.OriginalConstructorService;
import com.aklysoft.fantasyf1.service.original.drivers.OriginalDriver;
import com.aklysoft.fantasyf1.service.original.drivers.OriginalDriverService;
import com.aklysoft.fantasyf1.service.original.races.OriginalRace;
import com.aklysoft.fantasyf1.service.original.races.OriginalRaceService;
import com.aklysoft.fantasyf1.service.original.races.model.ERaceData;
import com.aklysoft.fantasyf1.service.original.results.model.ERaceResultExt;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class OriginalRaceResultService extends OriginalService<OriginalRaceResult, OriginalRaceResultPK> {

  private static final Logger LOGGER = LoggerFactory.getLogger(OriginalRaceService.class);

  private final ErgastDownloaderService downloaderService;
  private final OriginalRaceService originalRaceService;
  private final OriginalDriverService originalDriverService;
  private final OriginalConstructorService originalConstructorService;

  private final AppConfiguration appConfiguration;

  public OriginalRaceResultService(OriginalRaceResultRepository originalRaceResultRepository,
                                   @RestClient ErgastDownloaderService downloaderService,
                                   OriginalRaceService originalRaceService,
                                   OriginalDriverService originalDriverService,
                                   OriginalConstructorService originalConstructorService,
                                   AppConfiguration appConfiguration) {
    super(originalRaceResultRepository);
    this.downloaderService = downloaderService;
    this.originalRaceService = originalRaceService;
    this.originalDriverService = originalDriverService;
    this.originalConstructorService = originalConstructorService;
    this.appConfiguration = appConfiguration;
  }

  @Transactional
  public List<OriginalRaceResult> getRaceResults(final String series, final int year) {

    Supplier<Stream<ERaceResultExt>> supplier = () -> {
      LOGGER.info("downloading results of {} races of season {}", series, year);

      final ERaceData firstResp = downloaderService.getRaceResults(series, year, 0, appConfiguration.downloadLimit).getData();

      LOGGER.debug("total = {}, limit = {}", firstResp.getTotal(), firstResp.getLimit());

      return Stream.concat(
              Stream.of(firstResp),
              StreamUtils.repeat(firstResp.getLimit(), appConfiguration.downloadLimit, firstResp.getTotal(),
                      offset -> downloaderService.getRaceResults(series, year, offset, appConfiguration.downloadLimit).getData()))
              .flatMap(resp -> resp.getRaceTable().getRaces().stream())
              .flatMap(race -> race.getRaceResults()
                      .stream()
                      .map(raceResult ->
                              ERaceResultExt
                                      .builder()
                                      .race(race)
                                      .raceResult(raceResult)
                                      .build()));
    };

    return super.getAllBySeason(series, year, supplier,
            OriginalRaceResultMappers.getOriginalRaceResultIdMapper(series, year),
            getOriginalRaceResultMapper(series, year));
  }

  private BiFunction<ERaceResultExt, OriginalRaceResultPK, OriginalRaceResult> getOriginalRaceResultMapper(String series, int year) {
    return (data, id) -> {

      var originalRaceResult = OriginalRaceResultMappers.originalRaceResultMapper.apply(data, id);

      if (data.getRace() != null) {
        OriginalRace originalRace = originalRaceService.saveRace(series, data.getRace());
        originalRaceResult.setRace(originalRace);
      }

      if (data.getDriver() != null) {
        OriginalDriver driver = originalDriverService.saveDriver(series, year, data.getDriver());
        originalRaceResult.setDriverId(driver.getId());
        originalRaceResult.setDriver(driver);
      }

      if (data.getConstructor() != null) {
        OriginalConstructor constructor = originalConstructorService.saveConstructor(series, year, data.getConstructor());
        originalRaceResult.setConstructorId(constructor.getId());
        originalRaceResult.setConstructor(constructor);
      }

      return originalRaceResult;
    };
  }

  @Transactional
  public List<OriginalRaceResult> getCurrentRaceResult(String series) {

    LOGGER.info("downloading results of last {} race of current season", series);

    final ERaceData firstResp = downloaderService.getCurrentRaceResult(series, 0, appConfiguration.downloadLimit).getData();

    int year = firstResp.getRaceTable().getSeason();

    var idMapper = OriginalRaceResultMappers.getOriginalRaceResultIdMapper(series, year);

    var entityMapper = getOriginalRaceResultMapper(series, year);


    return Stream.concat(
            Stream.of(firstResp),
            StreamUtils.repeat(firstResp.getLimit(), appConfiguration.downloadLimit, firstResp.getTotal(),
                    offset -> downloaderService.getCurrentRaceResult(series, offset, appConfiguration.downloadLimit).getData()))
            .flatMap(resp -> resp.getRaceTable().getRaces().stream())
            .flatMap(race -> race.getRaceResults()
                    .stream()
                    .map(raceResult ->
                            ERaceResultExt
                                    .builder()
                                    .race(race)
                                    .raceResult(raceResult)
                                    .build()))
            .map(e -> entityMapper.apply(e, idMapper.apply(e)))
            .map(this::saveOriginalRaceResult)
            .collect(Collectors.toList());
  }

  private OriginalRaceResult saveOriginalRaceResult(OriginalRaceResult originalRaceResult) {

    var id = OriginalRaceResultPK
            .builder()
            .series(originalRaceResult.getSeries())
            .season(originalRaceResult.getSeason())
            .round(originalRaceResult.getRound())
            .position(originalRaceResult.getPosition())
            .build();

    return super.saveEntity(id, originalRaceResult);
  }

  @Transactional
  public List<OriginalRaceResult> getRaceResult(String series, Integer year, Integer round) {
    return ((OriginalRaceResultRepository) originalRepository).findAllBySeasonAndRace(series, year, round);
  }

}
