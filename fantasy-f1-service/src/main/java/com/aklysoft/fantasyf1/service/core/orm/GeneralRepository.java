package com.aklysoft.fantasyf1.service.core.orm;

import java.util.List;

public interface GeneralRepository<T, ID> {
  void persist(T entity);

  void delete(T entity);

  T findById(ID id);

  boolean exists(ID id);

  List<T> findAll();

  void deleteById(ID id);

  T update(T entity);
}
