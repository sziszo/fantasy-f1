package com.aklysoft.fantasyf1.service.core.orm;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public abstract class GeneralRepositoryImpl<T, ID> implements GeneralRepository<T, ID> {

  @PersistenceContext
  private EntityManager entityManager;

  private Class<T> entityClazz;
  private Class<ID> idClass;

  public GeneralRepositoryImpl(Class<T> entityClazz, Class<ID> idClass) {
    this.entityClazz = entityClazz;
    this.idClass = idClass;
  }

  @Override
  public void persist(T entity) {
    entityManager.persist(entity);
  }

  @Override
  public void delete(T entity) {
    entityManager.remove(entity);
  }

  @Override
  public T findById(ID id) {
    return entityManager.find(entityClazz, id);
  }

  @Override
  public List<T> findAll() {
    return entityManager.createQuery("FROM " + entityClazz.getSimpleName(), entityClazz).getResultList();
  }

  @Override
  public void deleteById(ID id) {
    T entity = findById(id);
    delete(entity);
  }
}
