package com.aklysoft.fantasyf1.service.users;

import com.aklysoft.fantasyf1.service.core.orm.GeneralRepositoryImpl;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepositoryImpl extends GeneralRepositoryImpl<User, String> implements UserRepository {
  public UserRepositoryImpl() {
    super(User.class, String.class);
  }
}
