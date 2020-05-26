package com.aklysoft.fantasyf1.service.original;

import com.aklysoft.fantasyf1.service.original.drivers.OriginalDriverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class OriginalService<T, ID> {

  private static final Logger LOGGER = LoggerFactory.getLogger(OriginalDriverService.class);

  protected OriginalRepository<T, ID> originalRepository;

  protected OriginalService() {  //required by CDI
  }

  protected OriginalService(OriginalRepository<T, ID> originalRepository) {
    this.originalRepository = originalRepository;
  }

  protected <E> List<T> getAllBySeason(String series, int year,
                                       Supplier<Stream<E>> supplier,
                                       Function<E, ID> idMapper,
                                       BiFunction<E, ID, T> entityMapper) {

    List<T> entities = originalRepository.findAllBySeason(series, year);
    if (entities == null || entities.isEmpty()) {

      entities = supplier.get()
              .map(e -> entityMapper.apply(e, idMapper.apply(e)))
              .peek(originalRepository::persist)
              .collect(Collectors.toList());
    }
    return entities;
  }


  protected <E> T saveEntity(E data, Function<E, ID> idMapper, BiFunction<E, ID, T> entityMapper) {

    var id = idMapper.apply(data);
    var entity = entityMapper.apply(data, id);

    return saveEntity(id, entity);
  }

  protected T saveEntity(ID id, T entity) {
    if (this.originalRepository.exists(id)) {
      entity = this.originalRepository.update(entity);
    } else {
      this.originalRepository.persist(entity);
    }

    return entity;
  }


}
