package com.aklysoft.fantasyf1.service.users;

import io.quarkus.elytron.security.common.BcryptUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Adds a new user in the database
   *
   * @param username the user name
   * @param password the unencrypted password (it will be encrypted with bcrypt)
   * @param role     the comma-separated roles
   */
  @Transactional
  public void addUser(String username, String password, String role) {
    userRepository.persist(
            User.builder()
                    .username(username)
                    .password(BcryptUtil.bcryptHash(password))
                    .role(role)
                    .build()
    );
  }

}
