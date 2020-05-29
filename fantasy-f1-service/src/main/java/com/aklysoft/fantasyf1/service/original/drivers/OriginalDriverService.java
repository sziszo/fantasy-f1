package com.aklysoft.fantasyf1.service.original.drivers;

import com.aklysoft.fantasyf1.service.core.AppConfiguration;
import com.aklysoft.fantasyf1.service.core.utils.StreamUtils;
import com.aklysoft.fantasyf1.service.original.ErgastDownloaderService;
import com.aklysoft.fantasyf1.service.original.OriginalService;
import com.aklysoft.fantasyf1.service.original.constructors.OriginalConstructorService;
import com.aklysoft.fantasyf1.service.original.drivers.model.EDriver;
import com.aklysoft.fantasyf1.service.original.drivers.model.EDriverData;
import com.aklysoft.fantasyf1.service.original.drivers.model.EDriverTable;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

@ApplicationScoped
public class OriginalDriverService extends OriginalService<OriginalDriver, OriginalDriverPK> {
  private static final Logger LOGGER = LoggerFactory.getLogger(OriginalDriverService.class);

  private final ErgastDownloaderService downloaderService;
  private final AppConfiguration appConfiguration;
  private final OriginalConstructorService originalConstructorService;

  @Inject
  public OriginalDriverService(OriginalDriverRepository originalDriverRepository, @RestClient ErgastDownloaderService downloaderService,
                               AppConfiguration appConfiguration, OriginalConstructorService originalConstructorService) {
    super(originalDriverRepository);
    this.downloaderService = downloaderService;
    this.appConfiguration = appConfiguration;
    this.originalConstructorService = originalConstructorService;
  }

  @Transactional
  public List<OriginalDriver> getDrivers(String series, int year) {

    Supplier<Stream<EDriver>> supplier = () -> {
      LOGGER.info("downloading {} drivers of {} season", series, year);
      final EDriverData firstData = downloaderService.getDrivers(series, year, 0, appConfiguration.downloadLimit).getData();

      return Stream.concat(
              Stream.of(firstData),
              StreamUtils.repeat(firstData.getLimit(), appConfiguration.downloadLimit, firstData.getTotal(),
                      offset -> downloaderService.getDrivers(series, year, offset, appConfiguration.downloadLimit).getData()))
              .flatMap(data -> data.getDriverTable().getDrivers().stream());
    };

    return super.getAllBySeason(series, year, supplier,
            OriginalDriverMappers.getDriverIdMapper(series, year),
            OriginalDriverMappers.driverMapper
    );
  }

  @Transactional
  public OriginalDriver saveDriver(String series, int year, EDriver driver) {
    return saveEntity(
            driver,
            OriginalDriverMappers.getDriverIdMapper(series, year),
            OriginalDriverMappers.driverMapper
    );
  }


  public OriginalDriver getDriver(String series, int season, String id) {
    return originalRepository.findById(
            OriginalDriverPK
                    .builder()
                    .series(series)
                    .season(season)
                    .id(id)
                    .build()
    );
  }

  @Transactional
  public void linkConstructorsAndDrivers(String series, int year) {

    originalConstructorService.getConstructors(series, year)
            .stream()
            .map(constructor -> downloaderService.getConstructorDrivers(series, year, constructor.getId()))
            .map(resp -> resp.getData().getDriverTable())
            .forEach(driverTable -> {
              String constructorId = driverTable.getConstructorId();
              driverTable.getDrivers()
                      .stream()
                      .map(EDriver::getDriverId)
                      .map(driverId -> getDriver(series, year, driverId))
                      .forEach(originalDriver -> originalDriver.setConstructorId(constructorId));
            });

  }

}
