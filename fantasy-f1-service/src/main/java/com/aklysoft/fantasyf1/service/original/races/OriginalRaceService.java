package com.aklysoft.fantasyf1.service.original.races;

import com.aklysoft.fantasyf1.service.core.AppConfiguration;
import com.aklysoft.fantasyf1.service.core.utils.StreamUtils;
import com.aklysoft.fantasyf1.service.original.ErgastDownloaderService;
import com.aklysoft.fantasyf1.service.original.OriginalService;
import com.aklysoft.fantasyf1.service.original.races.model.ERace;
import com.aklysoft.fantasyf1.service.original.races.model.ERaceData;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

@ApplicationScoped
public class OriginalRaceService extends OriginalService<OriginalRace, OriginalRacePK> {

  private static final Logger LOGGER = LoggerFactory.getLogger(OriginalRaceService.class);


  private final ErgastDownloaderService downloaderService;
  private final AppConfiguration appConfiguration;

  public OriginalRaceService(OriginalRaceRepository originalRaceRepository,
                             @RestClient ErgastDownloaderService downloaderService,
                             AppConfiguration appConfiguration) {
    super(originalRaceRepository);
    this.downloaderService = downloaderService;
    this.appConfiguration = appConfiguration;
  }

  @Transactional
  public List<OriginalRace> getRaces(final String series, final int year) {

    Supplier<Stream<ERace>> supplier = () -> {
      LOGGER.info("downloading {} races of {} season", series, year);

      final ERaceData firstData = downloaderService.getRaces(series, year, 0, appConfiguration.downloadLimit).getData();

      return Stream.concat(
              Stream.of(firstData),
              StreamUtils.repeat(firstData.getLimit(), appConfiguration.downloadLimit, firstData.getTotal(),
                      offset -> downloaderService.getRaces(series, year, offset, appConfiguration.downloadLimit).getData()))
              .flatMap(data -> data.getRaceTable().getRaces().stream());

    };

    return super.getAllBySeason(series, year, supplier,
            OriginalRaceMappers.getOriginalRaceIdMapper(series),
            OriginalRaceMappers.originalRaceMapper);
  }

  public OriginalRace saveRace(String series, ERace race) {
    return saveEntity(
            race,
            OriginalRaceMappers.getOriginalRaceIdMapper(series),
            OriginalRaceMappers.originalRaceMapper
    );
  }
}
