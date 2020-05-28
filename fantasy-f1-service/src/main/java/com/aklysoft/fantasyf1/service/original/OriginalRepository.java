package com.aklysoft.fantasyf1.service.original;

import com.aklysoft.fantasyf1.service.core.orm.GeneralRepository;

import java.util.List;

public interface OriginalRepository<T, ID> extends GeneralRepository<T, ID> {
  List<T> findAllBySeason(String series, int year);
}
