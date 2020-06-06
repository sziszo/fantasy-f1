package com.aklysoft.fantasyf1.service.original.constructors;

import com.aklysoft.fantasyf1.service.core.AppConfiguration;
import com.aklysoft.fantasyf1.service.core.utils.StreamUtils;
import com.aklysoft.fantasyf1.service.original.ErgastDownloaderService;
import com.aklysoft.fantasyf1.service.original.OriginalService;
import com.aklysoft.fantasyf1.service.original.constructors.model.EConstructor;
import com.aklysoft.fantasyf1.service.original.constructors.model.EConstructorData;
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
public class OriginalConstructorService extends OriginalService<OriginalConstructor, OriginalConstructorPK> {
  private static final Logger LOGGER = LoggerFactory.getLogger(OriginalConstructorService.class);

  private final ErgastDownloaderService downloaderService;
  private final AppConfiguration appConfiguration;

  @Inject
  public OriginalConstructorService(OriginalConstructorRepository originalConstructorRepository,
                                    @RestClient ErgastDownloaderService downloaderService,
                                    AppConfiguration appConfiguration) {
    super(originalConstructorRepository);
    this.downloaderService = downloaderService;
    this.appConfiguration = appConfiguration;
  }

  @Transactional
  public List<OriginalConstructor> getConstructors(String series, int season) {

    Supplier<Stream<EConstructor>> supplier = () -> {
      LOGGER.info("downloading {} constructors of {} season", series, season);
      final EConstructorData firstData = downloaderService.getConstructors(series, season, 0, appConfiguration.downloadLimit).getData();

      return Stream.concat(
              Stream.of(firstData),
              StreamUtils.repeat(firstData.getLimit(), appConfiguration.downloadLimit, firstData.getTotal(),
                      offset -> downloaderService.getConstructors(series, season, offset, appConfiguration.downloadLimit).getData()))
              .flatMap(data -> data.getConstructorTable().getConstructors().stream());
    };

    return super.getAllBySeason(series, season, supplier,
            OriginalConstructorMappers.getConstructorIdMapper(series, season),
            OriginalConstructorMappers.constructorMapper
    );
  }

  @Transactional
  public OriginalConstructor saveConstructor(String series, int year, EConstructor constructor) {
    return saveEntity(
            constructor,
            OriginalConstructorMappers.getConstructorIdMapper(series, year),
            OriginalConstructorMappers.constructorMapper
    );
  }

  public OriginalConstructor getConstructor(String series, int season, String id) {
    return originalRepository.findById(
            OriginalConstructorPK
                    .builder()
                    .series(series)
                    .season(season)
                    .id(id)
                    .build()
    );
  }


}
