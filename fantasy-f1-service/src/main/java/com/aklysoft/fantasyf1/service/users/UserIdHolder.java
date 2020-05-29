package com.aklysoft.fantasyf1.service.users;

import io.quarkus.security.identity.SecurityIdentity;

import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;

@ApplicationScoped
public class UserIdHolder implements Serializable {

  private final SecurityIdentity securityIdentity;

  public UserIdHolder(SecurityIdentity securityIdentity) {
    this.securityIdentity = securityIdentity;
  }

  public String getCurrentUserName() {
    return securityIdentity.getPrincipal().getName();
  }

}
